package cn.web.service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

//redis分布式锁
public class RedisLock {
	private ReentrantLock lock = new ReentrantLock();
	private int timeout = 2000; //锁超时
	private RedisService redisService;
	private String lockid; //key
	
	public RedisLock(RedisService redisService, String lockid) {
		this.redisService=redisService;
		this.lockid=lockid;
	}

	public boolean lock(long timeout,TimeUnit timeUnit) throws InterruptedException{
		timeout = timeUnit.toMillis(timeout);
		long time = timeout + System.currentTimeMillis();
		lock.tryLock(timeout, timeUnit);
		try{
			while(true){
				boolean hasLock = tryLock();
				if(hasLock){
					return true; //获得锁
				}else if(timeout < System.currentTimeMillis()){
					break;
				}
				Thread.sleep(1000);
			}
		}finally{
			if(lock.isHeldByCurrentThread()){
				lock.unlock();
			}
		}
		return false;
		}

		public boolean tryLock(){
			long now =  System.currentTimeMillis();
			String expires = String.valueOf(timeout+now);
			if(redisService.setnx(lockid, expires)>0){
				//获取锁，设置超时时间
				setLockStatus(expires);
				return true;
			}else{
				String locktime = redisService.get(lockid);
				//检查锁是否超时
				if(locktime != null && Long.parseLong(locktime)<now){
					String oldlocktime = redisService.getset(lockid,expires);
					//旧值与当前时间比较
					if(oldlocktime !=null && locktime.equals(oldlocktime)){
						//获取锁，设置超时时间
						setLockStatus(expires);
						return true;
					}
				}
				return false;
			}
		}
		
		private void setLockStatus(String expires){
			redisService.del(lockid);
			redisService.setnx(lockid, expires);
		}

		//释放锁
		public boolean unlock(){
//			if(lockHolder == Thread.currentThread()){
//				//判断锁是否超时，没有超时才将互斥量删除
//				if(lockExpiresTime > System.currentTimeMillis()){
//					redisService.del("lock.id");
//				}
//				lockHolder = null;
//				return true;
//			}else{
//				throws new IllegalMonitorStateException("无法执行解锁操作");
//			}
			return false;
		}
}
