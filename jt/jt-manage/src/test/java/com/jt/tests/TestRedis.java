package com.jt.tests;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.Transaction;


@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRedis {
   @Autowired(required = false)
	private Jedis jedis;
	
	@Autowired(required = false)
	private ShardedJedis shardJedis;
	//测试redis集群
	//集群和哨兵的区别?
	/**
	 * 1.哨兵模式监控权交给哨兵,由哨兵负责选举从机做为新的主机,完成故障转移,存放的数据都是相同的,浪费内存
	 * 2.集群模式是工作节点自己做监控,发生故障时,主节点具有选举权,在从节点中选举一个新的主节点完成故障转移.16384个槽,数据分布式存储
	 */
	@Autowired(required = false)
	private JedisCluster jc;
	@Test
	public void testClusters() {
		System.out.println(jc);
		Map<String, JedisPool> clusterNodes = jc.getClusterNodes();
		Class<? extends JedisCluster> class1 = jc.getClass();
		System.out.println(class1);
		System.out.println(jc);
		jc.set("155", "你怎么还失败");
		System.out.println(jc.get("155"));
		
	}
	
	
	//原生版本
	@Test
	public void testCluster() {
		String host="192.168.11.128";
		HashSet<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort(host, 7000));
		nodes.add(new HostAndPort(host, 7001));
		nodes.add(new HostAndPort(host, 7002));
		nodes.add(new HostAndPort(host, 7003));
		nodes.add(new HostAndPort(host, 7004));
		nodes.add(new HostAndPort(host, 7005));
		JedisCluster jedisCluster = new JedisCluster(nodes);
		jedisCluster.set("你好", "好你妈的");
		System.out.println(jedisCluster.get("你好"));
		
	}
	//测试哨兵 高可用的工作模式
	@Test
	public void testSentinel() {
		//在linux系统中配置哨兵主从结构,并搭建哨兵配置sentinel.conf
		HashSet<String> sentinels = new HashSet<>();
		//将哨兵sentinel的ip和端口保存到一个set集合 1.去重复的哨兵配置 2.可配置多哨兵
		sentinels.add("192.168.11.128:26379");
		//通过哨兵获得当前主机的端口 
		JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels);
		Jedis jedis = pool.getResource();
		jedis.set("1905", "才上三个月课");
		System.out.println(jedis.get("1905"));
		
	}
	
	//实现分片测试 端口6379 6380 6381
	//分片模式将不同的key分配到不同的redis节点,哈希运算,当增加或减少节点时,将有大量的key无法命中
	//不能宕机!!!
	@Test
	public void testShards() {
		List<JedisShardInfo> shards=new ArrayList<JedisShardInfo>();
		String host="192.168.11.128";
		shards.add(new JedisShardInfo(host,6379));
		shards.add(new JedisShardInfo(host,6380));
		shards.add(new JedisShardInfo(host,6381));
		@SuppressWarnings("resource")
		ShardedJedis jedis = new ShardedJedis(shards);
		jedis.set("1905", "要毕业了");
		System.out.println(jedis.get("1905"));
		
	}
	@Test
	public void testShard() {
		shardJedis.set("1905", "要毕业了");
		System.out.println(shardJedis.get("1905"));
	}
	
	
	@Test
	public void testS() {
		String host="192.168.11.128";
		int port=6379;
		jedis.set("1905", "redis的学习手册");
		String string = jedis.get("1905");
		jedis.expire("1905", 10);
		String setex = jedis.setex("2005", 10, "你猜我猜不猜");
		System.out.println(jedis.getClass());
		
	}
	

	@Test
	public void testString() {
		
		String host="192.168.11.128";
		int port=6379;
		Jedis jedis = new Jedis(host,port);
		jedis.set("1905", "redis的学习手册");
		String string = jedis.get("1905");
		jedis.expire("1905", 10);
		String setex = jedis.setex("2005", 10, "你猜我猜不猜");
		System.out.println(setex);
	}
	/*
	 * 如果redis中存在这个key 则不允许修改
	 * 当做标识 , 开关 boolean flag=true/false
	 */
	@Test
	public void testString_nx() {
		
		String host="192.168.11.128";
		int port=6379;
		Jedis jedis = new Jedis(host,port);
		jedis.setnx("1905", "redis的学习手册");
		jedis.setnx("1905", "演员的学习手册");
		System.out.println(jedis.get("1905"));
	}
	@Test
public void testString_ex_nx() {
		
		String host="192.168.11.128";
		int port=6379;
		Jedis jedis = new Jedis(host,port);
		jedis.set("1905", "6666", "NX", "EX", 30);
		System.out.println(jedis.get("1905"));
		
}
	

	
        public void testHash_ex_nx() {
		
		String host="192.168.11.128";
		int port=6379;
		this.jedis = new Jedis(host,port);
}
	
	@Test
	public void testHash() {
	jedis.hset("user", "id", "80");
	jedis.hset("user", "name", "张三");
	Map<String, String> hgetAll = jedis.hgetAll("user");
	Set<Entry<String, String>> entrySet = hgetAll.entrySet();
	Iterator<Entry<String, String>> iterator = entrySet.iterator();
	while(iterator.hasNext()) {
		Entry<String, String> next = iterator.next();
		String value = next.getValue();
		System.out.println(value);
		String key = next.getKey();
		System.out.println(key);
	}
	System.out.println(hgetAll);
	}
	
	/*
	 * redis的事物控制
	 */
	@Test
	public void testTransaction() {
		Transaction multi = jedis.multi();
		try{
		multi.set("1", "name");
		multi.get("1");
		int a=1/0;
		multi.exec();
		}
		catch(Exception e){
			
			
			multi.discard();
		}
	}
	
}






























