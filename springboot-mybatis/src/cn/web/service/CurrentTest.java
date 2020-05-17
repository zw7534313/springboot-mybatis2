package cn.web.service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class CurrentTest extends Thread {
	private RedisService redisService;
	private ReentrantLock lock;
	public CurrentTest(RedisService redisService, ReentrantLock lock) {
		this.redisService=redisService;
		this.lock=lock;
	}

	public void run() {
		/*
		lock.lock();
		Long total = Long.valueOf(redisService.get("total"));
		Long num = Long.valueOf(redisService.get("product.num"));
		System.out.println("num="+num+",total="+total);
		if(num<total) {
			redisService.incr("product.num");
		}else{
			System.out.println("num over!");
		}
		lock.unlock();
		*/
		RedisLock rl = new RedisLock(redisService, "lock1");
		long timeout = 1000;
		try {
			rl.lock(timeout, TimeUnit.MILLISECONDS);
			Long total = Long.valueOf(redisService.get("total"));
			Long num = Long.valueOf(redisService.get("product.num"));
			System.out.println("num="+num+",total="+total);
			if(num<total) {
				redisService.incr("product.num");
			}else{
				System.out.println("num over!");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
