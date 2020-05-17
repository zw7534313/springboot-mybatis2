package cn.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


/**
 * 需要可以将任意的类型转化为string类型，并且可以根据字符串还原出对象
 * 
 * get,set方法 调用时，key都是加上前缀后的key，并且可以设置过期时间。
 */
@Service
public class RedisService {
	private int expiredTime = 5;
	@Autowired
	private JedisPool jedisPool;
	
	public <T> T get(String prefix,String key,Class<T> clazz){
		Jedis j=null;
		try{
			j=jedisPool.getResource();				
			return string2Key(j.get(prefix+key) , clazz);
		}finally{
			j.close();
		}
	}
	
	public <T> void set(String prefix,String key,T value){
		Jedis j=null;
		try{
			j=jedisPool.getResource();				
			String v=key2String(value);
			System.out.println("v="+v);
			//j.set(prefix+key, v);
			j.setex(prefix+key, expiredTime, v);
		}finally{
			j.close();
		}
	}
	
	public <T> String key2String(T key){
		return JSON.toJSONString(key);
	}
	
	public <T> T string2Key(String key, Class<T> clazz){		
		return JSON.parseObject(key, clazz);
	}
	
	public Long incr(String key) {
		Jedis j=null;
		try{
			j=jedisPool.getResource();	
			return j.incr(key);
		}finally{
			j.close();
		}
	}
	
	public String get(String key) {
		Jedis j=null;
		try{
			j=jedisPool.getResource();	
			return j.get(key);
		}finally{
			j.close();
		}
	}
	
	public String getset(String key, String value) {
		Jedis j=null;
		try{
			j=jedisPool.getResource();	
			return j.getSet(key, value);
		}finally{
			j.close();
		}
	}
	
	public Long setnx(String key, String value) {
		Jedis j=null;
		try{
			j=jedisPool.getResource();	
			return j.setnx(key, value);
		}finally{
			j.close();
		}
	}
	
	public String set(String key, String value) {
		Jedis j=null;
		try{
			j=jedisPool.getResource();	
			return j.set(key, value);
		}finally{
			j.close();
		}
	}
	public Long del(String key) {
		Jedis j=null;
		try{
			j=jedisPool.getResource();	
			return j.del(key);
		}finally{
			j.close();
		}
	}
	
}
