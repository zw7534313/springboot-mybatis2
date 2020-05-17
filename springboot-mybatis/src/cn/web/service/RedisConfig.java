package cn.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class RedisConfig {
	@Value("${spring.redis.host}")
	private String host;
	@Value("${spring.redis.port}")
	private int port;
	@Value("${spring.redis.timeout}")
	private int timeout;
	@Value("${spring.redis.password}")
	private String password;
	
	@Value("${spring.redis.jedis.pool.max-active}")
	private int poolMaxTotal;
	@Value("${spring.redis.jedis.pool.max-idle}")
	private int poolMaxIdel;
	@Value("${spring.redis.jedis.pool.max-wait}")
	private int poolMaxWait;
	
	@Bean
	public JedisPoolConfig getGenericObjectPoolConfig(){
		JedisPoolConfig config=new JedisPoolConfig();
		config.setMaxTotal(poolMaxTotal);
		config.setMaxIdle(poolMaxIdel);
		config.setMaxWaitMillis(poolMaxWait);
		config.setTestOnBorrow(true);
		return config;
	}
	
	@Bean
	public JedisPool getJedisPool(JedisPoolConfig config){
		JedisPool pool=new JedisPool(host,port);
				//new JedisPool(config,host,port,timeout,password);
		
		return pool;
	}
	
	@Bean
	public Jedis getJedis(){
		return new Jedis(host,port);
	}
	
}
