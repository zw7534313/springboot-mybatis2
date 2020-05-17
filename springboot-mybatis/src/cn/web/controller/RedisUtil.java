package cn.web.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil implements InitializingBean{
	
	private RedisUtil() {
	}
	
	private static RedisUtil instance = null;
	@Autowired
	private RedisTemplate redisTemplate;
	
	public synchronized static RedisUtil getInstance() {
		if(instance==null) {
			instance = new RedisUtil();
		}
		return instance;
	}
	
	@Bean
	private ApplicationContext getApp() {
		return ApplicationContextUtil.getApplicationContext();
	}
	
	@PostConstruct
	public void init() {
		System.out.println("@PostConstruct..");
		if(instance == null) {
			instance = getInstance();
		}
		instance.redisTemplate = redisTemplate;
		initRedisTemplate();
	}
	
	//设置redistemplate的序列化器
	private void initRedisTemplate(){
		System.out.println("默认key序列化器："+ApplicationContextUtil.getApplicationContext()+instance.redisTemplate.getKeySerializer());
		System.out.println("默认value序列化器："+instance.redisTemplate.getValueSerializer());
		RedisSerializer rs = instance.redisTemplate.getStringSerializer();
		instance.redisTemplate.setKeySerializer(rs);
		instance.redisTemplate.setValueSerializer(rs);
	}
	
	public void afterPropertiesSet() throws Exception {
		System.out.println("InitializingBean..");
	}

	public String get(String key) {
		byte[] bval = instance.redisTemplate.getConnectionFactory().getConnection().get(key.getBytes());
		return new String(bval);
	}
}
