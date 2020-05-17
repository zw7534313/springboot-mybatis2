package cn.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.alibaba.fastjson.JSON;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.SortingParams;

//redis切片链接池
public class RedisShardPoolUtil {
	public static ShardedJedisPool pool;
	static {
		try {
			List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
			shards.add(new JedisShardInfo("192.168.191.128", 6379));
			//shards.add(new JedisShardInfo("127.0.0.2", 6379));
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(300);
			config.setMaxIdle(600);
			config.setMaxWaitMillis(3000);
			pool = new ShardedJedisPool(config, shards);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static long setnax(String key, String value) {
		ShardedJedis client = null;
		try {
			client = pool.getResource();
			return client.setnx(key, value); //成功返回1
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			client.close();
		}
		return 0;
	}
	
	//对象存储为json串
	public static long setnx(String key, Object value) {
		ShardedJedis jedis = null;
		try {
			String json = JSON.toJSONString(value);
			jedis = pool.getResource();
			return jedis.setnx(key, json);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jedis.close();
		}
		return 0;
	}
	
	public static boolean del(String key) {
		ShardedJedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.del(key);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			jedis.close();
		}
	}
	
	public static Object get(String key) {
		ShardedJedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.get(key);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			jedis.close();
		}
	}
	
	public static <T> T get(String key,Class<T> clazz) {
		ShardedJedis jedis = null;
		try {
			jedis = pool.getResource();
			String value= jedis.get(key);
			return JSON.parseObject(value, clazz);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			jedis.close();
		}
	}
	
	public static boolean checkExists(String key) {
		ShardedJedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.exists(key);
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			jedis.close();
		}
	}
	
	//往指定的key追加内容
	public static boolean appendStr(String key, String value) {
		ShardedJedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.append(key, value);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			jedis.close();
		}
	}
	
	public static long hset(String key, String field,String value) {
		ShardedJedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hset(key, field, value);
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}finally {
			jedis.close();
		}
	}
	
	public static Object hget(String key, String field) {
		ShardedJedis jedis = null;
		try {
			jedis = pool.getResource();
		return jedis.hget(key, field);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			jedis.close();
		}
	}
	
	public static Object hmset(String key, Map<String, String> map) {
		ShardedJedis jedis = null;
		try {
			jedis = pool.getResource();
		return jedis.hmset(key, map); //成功返回OK
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			jedis.close();
		}
	}
	
	public static List<String> hmget(String key, String...fields) {
		ShardedJedis jedis = null;
		try {
			jedis = pool.getResource();
		return jedis.hmget(key, fields);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			jedis.close();
		}
	}
	
	public static Map<String,String> getAll(String key) {
		ShardedJedis jedis = null;
		try {
			jedis = pool.getResource();
		return jedis.hgetAll(key);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			jedis.close();
		}
	}
	
	//指定自增，负数自减
	public static long hincrby(String key, String field,long value) {
		ShardedJedis jedis = null;
		try {
			jedis = pool.getResource();
		return jedis.hincrBy(key, field, value);
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}finally {
			jedis.close();
		}
	}
	
		public static boolean hdel(String key, String...fields) {
			ShardedJedis jedis = null;
			try {
				jedis = pool.getResource();
				jedis.hdel(key, fields);
				return true;
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}finally {
				jedis.close();
			}
		}
		
		public static long lpush(String key, String...values) {
			ShardedJedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.lpush(key, values);
			}catch(Exception e) {
				e.printStackTrace();
				return 0;
			}finally {
				jedis.close();
			}
		}
		
		public static long rpush(String key, String...values) {
			ShardedJedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.rpush(key, values);
			}catch(Exception e) {
				e.printStackTrace();
				return 0;
			}finally {
				jedis.close();
			}
		}
		
		public static List<String> lrange(String key, long start, long end) {
			ShardedJedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.lrange(key, start, end);
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}finally {
				jedis.close();
			}
		}
		//从头部删除元素，并返回删除元素
		public static String lpop(String key) {
			ShardedJedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.lpop(key);
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}finally {
				jedis.close();
			}
		}
		
		public static String lindex(String key, int index) {
			ShardedJedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.lindex(key, index);
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}finally {
				jedis.close();
			}
		}
		
		public static long llen(String key) {
			ShardedJedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.llen(key);
			}catch(Exception e) {
				e.printStackTrace();
				return 0;
			}finally {
				jedis.close();
			}
		}
		//集合
		public static long sadd(String key, String...values) {
			ShardedJedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.sadd(key, values);
			}catch(Exception e) {
				e.printStackTrace();
				return 0;
			}finally {
				jedis.close();
			}
		}
		
		//集合个数
				public static long slen(String key) {
					ShardedJedis jedis = null;
					try {
						jedis = pool.getResource();
						return jedis.scard(key);
					}catch(Exception e) {
						e.printStackTrace();
						return 0;
					}finally {
						jedis.close();
					}
				}
				
				public static Set<String> sall(String key) {
					ShardedJedis jedis = null;
					try {
						jedis = pool.getResource();
						return jedis.smembers(key);
					}catch(Exception e) {
						e.printStackTrace();
						return null;
					}finally {
						jedis.close();
					}
				}
				
				//有序集合添加一个或多个
				public static long saddorder(String key, Map<String,Double> value) {
					ShardedJedis jedis = null;
					try {
						jedis = pool.getResource();
						return jedis.zadd(key, value);
					}catch(Exception e) {
						e.printStackTrace();
						return 0;
					}finally {
						jedis.close();
					}
				}
				
				//排序
				public static List<String> sort(String key, SortingParams params) {
					ShardedJedis jedis = null;
					try {
						jedis = pool.getResource();
						return jedis.sort(key, params);
					}catch(Exception e) {
						e.printStackTrace();
						return null;
					}finally {
						jedis.close();
					}
				}
				
				//排序:从大到小
				public static List<String> sort(String key) {
					ShardedJedis jedis = null;
					try {
						jedis = pool.getResource();
						SortingParams par = new SortingParams();
						par.desc();
						return jedis.sort(key, par);
					}catch(Exception e) {
						e.printStackTrace();
						return null;
					}finally {
						jedis.close();
					}
				}
}
