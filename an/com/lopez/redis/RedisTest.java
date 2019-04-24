package an.com.lopez.redis;

import redis.clients.jedis.Jedis;

public class RedisTest {
	public static void main(String[] args) {
		Jedis jedis=new Jedis("localhost");
		System.out.println("connection success");
		System.out.println("Server is running:"+jedis.ping());
		jedis.set("rKey", "www.baidu.com");
	}
}
