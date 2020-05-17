package cn.web.controller;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TimeTask {
	
	@Scheduled(cron="0/2 * * * * ?") //每2秒执行一次
	public void callTime() {
		System.out.println(Thread.currentThread().getName() +"start.. "+new Date());
	}
	/*
	@Scheduled(cron="0/2 * * * * ?") //每2秒执行一次,方法执行超过2秒，上一次执行完以后暂停2秒开始执行下一次任务
	public void callTime() {
		System.out.println("start.. "+new Date());
		try {
			Thread.sleep(3000); 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	/*
	@Scheduled(cron="0 10 19 ? * *") //每天19:10触发
	public void callTime() {
		System.out.println("start.. "+new Date());
	}*/
	
//	"0 15 10 ? * MON-FRI" 周一至周五的上午10:15触发 
//	"0 15 10 15 * ?" 每月15日上午10:15触发 
//	"0 15 10 L * ?" 每月最后一日的上午10:15触发 
//	"0 15 10 ? * 6L" 每月的最后一个星期五上午10:15触发 
	/*@Scheduled(cron="0 0/2 19 * * ?") //在每天下午7点到下午7:55期间的每2分钟触发
	public void callTime() {
		System.out.println("start.. "+new Date());
	}*/
	
	@Scheduled(initialDelay=1000,fixedRate=2000) //在容器启动后,延迟1秒后执行一次定时器,以后每2秒再执行一次该定时器
	public void callTime2() {
		System.out.println(Thread.currentThread().getName() + "start2.. "+new Date());
	}
}
