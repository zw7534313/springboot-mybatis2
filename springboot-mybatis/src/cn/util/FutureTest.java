package cn.util;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

//1.使用Callable返回执行结果（获取返回值get()是阻塞方法）
public class FutureTest {
	public static void main(String[] args) throws Exception{
		FutureTask<String> future = new FutureTask<String>(new CallableTask("hi"));
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.submit(future);
		System.out.println("提交任务 "+new Date());
		
		Thread.sleep(1000);
		System.out.println("任务执行结果：" + future.get() + new Date()); //get()阻塞
		System.out.println("game over!");
		executor.shutdown();
	}
}

class CallableTask implements Callable<String>{
	private String para;
	public String call() throws Exception {
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<10;i++) {
			sb.append(para);
			Thread.sleep(300);
		}
		return sb.toString();
	}
	public CallableTask(String para) {
		this.para=para;
	}
}
