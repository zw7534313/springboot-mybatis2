package cn.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import cn.web.mapper.RedPacketMapper;
import cn.web.po.RedPacketPO;
import cn.web.po.RedPacketUserPO;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedPacketServiceImpl implements IRedPacketService {

	@Resource
	private RedPacketMapper redPacketMapper;
	
	@Autowired
	private JedisPool jedisPool;
	
	//批量update领取红包的用户ID到数据库
	private ScheduledExecutorService executor =
			Executors.newSingleThreadScheduledExecutor(newThreadFactory("save-redpacket", false));
	
	public RedPacketPO getRedPacket(Integer id) {
		return redPacketMapper.getRedPacket(id);
	}

	public int decreaseRedPacket(Integer id) {
		return redPacketMapper.updateRedPacketStock(id);
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public int grapRedPacket(Integer redPacketId, Integer userId) {
		// 获取红包信息
	    RedPacketPO redPacket = redPacketMapper.getRedPacket(redPacketId);
	    int leftRedPacket = redPacket.getStock();
	    // 当前小红包库存大于0
	    if (leftRedPacket > 0) {
	      redPacketMapper.updateRedPacketStock(redPacketId);
	      // 生成抢红包信息
	      RedPacketUserPO userRedPacket = new RedPacketUserPO();
	      userRedPacket.setRedPacketId(redPacketId);
	      userRedPacket.setUserId(userId);
	      userRedPacket.setAmount(5);
	      // 插入抢红包信息
	      int result = redPacketMapper.insertRedPacketUser(userRedPacket);
	      return result;
	    }
	    //没有红包啦.....剩余Stock数量
	    return -1;
	}
	
	public int grapRedPacketProc(Integer redPacketId, Integer userId) {
		int num = -1;
		Map<String,Integer> map = new HashMap<String, Integer>();
		map.put("v_red_packet_id", redPacketId);
		map.put("v_user_id", userId);
		redPacketMapper.grabRedPacketProcedure(map);
		if(map.containsKey("num")) {
			num = map.get("num");
		}
		return num;
	}
	
	/**
	   * 生成小红包并放入redis队列：创建大红包
	   * @param amount
	   * @param count
	   */
	  public void createSmallRedPackets(double amount, Integer count) {
		  //保存大红包信息
		  int bigRedPacketId = 1;
		  
		  //创建红包未消费队列、已消费队列、已消费用户id的Map
		  String mapKey = "redpacket."+bigRedPacketId;
//		  createUserIdMap(mapKey); //大红包id
		  
		  //假如生成等额的count个红包
		  for(int i=0;i<count;i++) {
			  RedPacketUserPO po = new RedPacketUserPO();
			  po.setAmount(5);
			  po.setRedPacketId(1);
			  redPacketMapper.insertRedPacketUser(po);
			  
			  System.out.println("id="+po.getId());
			  
			  redPacketMapper.updateRedPacketStock(po.getRedPacketId());
			 //红包未消费队列添加小红包
			 String redPacketKey = mapKey+".queue1";
			 pushRedPacket(redPacketKey, po);
		  }
	  }
	  
	  /**
	   * 通过redis队列来抢红包
	   * @param redPacketId
	   * @param userId
	   * @return
	   */
	  public int grapRedPacketRedis(Integer redPacketId, Integer userId) {
		  String mapKey = "redpacket."+redPacketId;
			//判断是否领取过
		  if(exists(mapKey, userId+"")) {
			  System.out.println("领取过 userId="+userId);
			  return 0;
		  } else {
			  System.out.println("未领取 userId="+userId);
			  //从未消费队列中获取红包
			  RedPacketUserPO redPacket = popRedPacket(mapKey+".queue1");
			  if(redPacket != null) {
				  //放入已消费队列
				  String redPacketKey = mapKey+".queue2";
				  redPacket.setUserId(userId);
				  pushRedPacket(redPacketKey,redPacket);
				  
				  //已消费用户id的Map添加userId
				  addUserId(mapKey,userId+"");
				  return 1;
			  }
			  return 0;
		  }
	 }
	  /*
	  private void set(String key,String value){
			Jedis j=null;
			try{
				j=jedisPool.getResource();				
				j.set(key, value);
			}finally{
				j.close();
			}
		}
	  
	  private boolean exists(String key){
			Jedis j=null;
			try{
				j=jedisPool.getResource();				
				return j.exists(key);
			}finally{
				j.close();
			}
		}
	  */
	  //用于用户id去重: redis如何保存key-map<String,String>及对map的操作 -使用hash替代  废弃该方法
	  private void createUserIdMap(String key, String userId){
			Jedis j=null;
			//Map<String, String> userIdMap = new HashMap<String, String>();
			try{
				j=jedisPool.getResource();	
				//j.hmset(key, userIdMap);
				j.hset(key, userId, "");
			}finally{
				j.close();
			}
		}
	  
	  //存放list
	  private void pushRedPacket(String key,RedPacketUserPO value){
			Jedis j=null;
			try{
				j=jedisPool.getResource();	
				String v = key2String(value);
				j.rpush(key, v);
			}finally{
				j.close();
			}
		}
	  
	//获取红包
	  private RedPacketUserPO popRedPacket(String key){
			Jedis j=null;
			try{
				j=jedisPool.getResource();	
				String jsonstr = j.lpop(key);
				System.out.println("获取红包: "+jsonstr);
				//jsonstr 为空
				if(jsonstr == null) {
					return null;
				}
				return string2Key(jsonstr, RedPacketUserPO.class);
			}finally{
				j.close();
			}
		}
	  
	  //判断是否领取过
	  private boolean exists(String key, String userId){
			Jedis j=null;
			try{
				j=jedisPool.getResource();				
				return j.hexists(key, userId);
			}finally{
				j.close();
			}
		}
	  
	  //保存已消费用户id到map
	  private void addUserId(String key,String value){
			Jedis j=null;
			try{
				j=jedisPool.getResource();		
				j.hset(key, value, "");
			}finally{
				j.close();
			}
		}
	  
	  private static ThreadFactory newThreadFactory(final String processName, final boolean isDaemon) {
	        return new ThreadFactory() {
	            private AtomicInteger threadIndex = new AtomicInteger(0);

	            public Thread newThread(Runnable r) {
	                Thread thread = new Thread(r, String.format("%s_%d", processName, this.threadIndex.incrementAndGet()));
	                thread.setDaemon(isDaemon);
	                return thread;
	            }
	        };
	    }
	  private <T> String key2String(T key){
			return JSON.toJSONString(key);
	  }
	  
	  public <T> T string2Key(String key, Class<T> clazz){		
			return JSON.parseObject(key, clazz);
	  }
	  
	  public int updateRedPacketStatus(RedPacketPO po) {
		  return redPacketMapper.updateRedPacketStatus(po);
	  }
	  
	  public List<RedPacketPO> getRedPacketByStatus(){
		  return redPacketMapper.getRedPacketByStatus();
	  }
	  
	  //批量update领取红包的用户ID到数据库
	  private void saveRedPacket() {
		  System.out.println("批量update领取红包的用户ID到数据库..");
		  //从red_packet表中status取出 0-未保存状态 的大红包id
		  List<RedPacketPO> list= getRedPacketByStatus();
		  if(list == null || list.size() == 0) {
			  return ;
		  }
		  RedPacketPO redpacket = null;
		  for(int i=0;i<list.size();i++) {
			  redpacket = list.get(i);
			  String mapKey= "redpacket."+redpacket.getId();
			  //从消费队列中获取红包
			  RedPacketUserPO redPacket = null;
			  do {
				  redPacket = popRedPacket(mapKey+".queue2");
				  redPacketMapper.updateRedPacketUserId(redPacket);
			  } while(redPacket !=null);
			  
			  //更新大红包的status状态：1-已取出
			  RedPacketPO po = new RedPacketPO();
			  po.setId(redpacket.getId());
			  po.setStatus("1");
			  updateRedPacketStatus(po);
		  }
	  }
	  
	  {
		  //批量update领取红包的用户ID到数据库-定时保存
		  executor.scheduleAtFixedRate(new Runnable() {
	            @Override
	            public void run() {
	            	saveRedPacket();
	            }
	        }, 1000 * 300, 1000 * 300, TimeUnit.MILLISECONDS);
	  }

	
}
