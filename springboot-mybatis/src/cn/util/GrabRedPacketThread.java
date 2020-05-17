package cn.util;

import java.util.concurrent.atomic.AtomicInteger;

import cn.web.service.IRedPacketService;

public class GrabRedPacketThread implements Runnable{
	private IRedPacketService redPacketService;
	private AtomicInteger num = new AtomicInteger(0);
	
	public GrabRedPacketThread(IRedPacketService redPacketService) {
		this.redPacketService=redPacketService;
	}

	public void run() {
		int redPacketId =1;
		int userId = 0;
		for(int i=0;i<50;i++) {
		//redPacketService.grapRedPacket(redPacketId, num.getAndIncrement());
		
		//redPacketService.grapRedPacketProc(redPacketId, num.getAndIncrement());
			userId = num.incrementAndGet();
			redPacketService.grapRedPacketRedis(redPacketId, userId);
		}
	}

}
