package pers.husen.highdsa.service.redis;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pers.husen.highdsa.common.exception.StackTrace2Str;
import pers.husen.highdsa.common.utility.ReadConfigFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Desc redis连接池
 *
 * @Author 何明胜
 *
 * @Created at 2018年2月28日 上午9:51:35
 * 
 * @Version 1.0.1
 */
public class RedisPoolsImpl implements RedisPools {
	protected static JedisPool jedisPool = null;
	private static final Logger logger = LogManager.getLogger(RedisPoolsImpl.class.getName());

	@Override
	public void initRedis() throws UnsupportedEncodingException, IOException {
		ResourceBundle bundle = ReadConfigFile.readByClassPath("redis");
		if (bundle == null) {
			throw new IllegalArgumentException("[redis.properties] is not found!");
		}

		JedisPoolConfig jedisconfig = new JedisPoolConfig();

		jedisconfig.setMaxTotal(Integer.valueOf(bundle.getString("redis.pool.maxTotal")));
		jedisconfig.setMaxIdle(Integer.valueOf(bundle.getString("redis.pool.maxIdle")));
		jedisconfig.setMaxWaitMillis(Long.valueOf(bundle.getString("redis.pool.maxWait")));
		jedisconfig.setTestOnBorrow(Boolean.valueOf(bundle.getString("redis.pool.testOnBorrow")));
		jedisconfig.setTestOnReturn(Boolean.valueOf(bundle.getString("redis.pool.testOnReturn")));

		String authPwd = bundle.getString("redis.auth");
		if (authPwd == null || authPwd == "") {
			logger.info("redis连接没有密码");
			jedisPool = new JedisPool(jedisconfig, bundle.getString("redis.ip"), Integer.valueOf(bundle.getString("redis.port")), Integer.valueOf(bundle.getString("redis.timeout")));
		} else {
			logger.trace("redis连接有密码：{}", authPwd);
			jedisPool = new JedisPool(jedisconfig, bundle.getString("redis.ip"), Integer.valueOf(bundle.getString("redis.port")), Integer.valueOf(bundle.getString("redis.timeout")), authPwd);
		}

		logger.trace("redis 连接池初始化成功!");
	}

	@Override
	public synchronized Jedis getJedis() {
		Jedis jedis = null;

		if (jedisPool == null || jedisPool.isClosed()) {
			logger.trace("redis 连接池为空,开始初始化...");
			try {
				initRedis();
			} catch (IOException e) {
				logger.warn(StackTrace2Str.exceptionStackTrace2Str("初始化连接池错误,1秒后重试", e));

				try {
					Thread.sleep(1000);
					initRedis();
				} catch (InterruptedException inException) {
					logger.error(StackTrace2Str.exceptionStackTrace2Str("休眠1秒钟失败", inException));
				} catch (IOException ioException) {
					logger.error(StackTrace2Str.exceptionStackTrace2Str("第2次初始化连接池失败", ioException));
				}
			}
		}

		try {
			jedis = jedisPool.getResource();
			logger.trace("分配一个redis 连接池成功!");
		} catch (Exception e) {
			logger.error(StackTrace2Str.exceptionStackTrace2Str("分配连接池错误,开始重试", e));

			try {
				jedis = jedisPool.getResource();
			} catch (Exception exception) {
				logger.error(StackTrace2Str.exceptionStackTrace2Str("第2次初始化连接池失败", exception));
			}
		}

		return jedis;
	}

	@Override
	public void returnResource(final Jedis jedis) {
		if (jedis != null) {
			jedis.close();
			logger.trace("redis 连接释放1个");
		}
	}

	@Override
	public void closeRedisPool() {
		jedisPool.close();
		logger.info("关闭redis连接池成功!");
	}

	public static void main(String[] args) {
		/*
		 * Jedis jedis = RedisPools.getJedis(); System.out.println(jedis.set("name",
		 * "何明胜")); System.out.println(jedis.get("name"));
		 * RedisPools.returnResource(jedis);
		 * 
		 * RedisPools.closeRedisPool(); Jedis jedis1 = RedisPools.getJedis();
		 * System.out.println(jedis1.set("name", "何明胜"));
		 * System.out.println(jedis1.get("name"));
		 */

		int maxTotal = 26;
		RedisPoolsImpl redisPoolsImpl = new RedisPoolsImpl();

		for (int i = 1; i <= maxTotal; i++) {
			System.out.println("第" + i + "个");
			Jedis jedis2 = redisPoolsImpl.getJedis();
			System.out.println(jedis2.set("name", "何明胜"));
			System.out.println(jedis2.get("name1"));
		}
	}
}