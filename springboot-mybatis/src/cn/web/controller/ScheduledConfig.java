package cn.web.controller;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class ScheduledConfig implements SchedulingConfigurer {

	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(setExecutor());
	}

	@Bean(destroyMethod="shutdown")
	public Executor setExecutor(){
		return Executors.newScheduledThreadPool(5); // 5个线程来处理。
	}
}
