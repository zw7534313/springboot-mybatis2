package cn.web.service;

import java.util.List;

import cn.web.po.RedPacketPO;
import cn.web.po.RedPacketUserPO;

public interface IRedPacketService {

	/**
	   * 获取红包信息.
	   * @param id --红包id
	   * @return 红包具体信息
	   */
	  public RedPacketPO getRedPacket(Integer id);
	  
	  /**
	   * 扣减抢红包数.
	   * @param id -- 红包id
	   * @return 更新记录条数
	   */
	  public int decreaseRedPacket(Integer id);
	  
	  /**
	   * 抢红包
	   * @param redPacketId 红包id
	   * @param userId 用户id
	   * @return 影响记录数.
	   */
	  public int grapRedPacket(Integer redPacketId, Integer userId);
	  
	  /**
	   * 抢红包 使用存储过程
	   * @param redPacketId 红包id
	   * @param userId 用户id
	   * @return 影响记录数.
	   */
	  public int grapRedPacketProc(Integer redPacketId, Integer userId);
	  
	  /**
	   * 生成小红包并放入redis队列
	   * @param amount
	   * @param count
	   */
	  public void createSmallRedPackets(double amount, Integer count);
	  
	  /**
	   * 通过redis队列来抢红包
	   * @param redPacketId
	   * @param userId
	   * @return
	   */
	  public int grapRedPacketRedis(Integer redPacketId, Integer userId);
	  
	  /**
	   * 更新大红包状态 0-未保存 1-已存入数据库
	   * @param po
	   * @return
	   */
	  int updateRedPacketStatus(RedPacketPO po);
	  
	  /**
	   * 获取小红包未入库的大红包信息
	   * @return
	   */
	  List<RedPacketPO> getRedPacketByStatus();
}
