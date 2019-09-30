package com.jt.config;





import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

@Configuration
@PropertySource("classpath:/properties/clusterRedis.properties")
public class ClusterRedisConfig {

	@Value("${redis.nodes}")
	private String nodes;
	
	   @Bean()//方法的返回值 交给容器管理
		public JedisCluster jedisCluster() {
		
			Set<HostAndPort> node = new HashSet<>();
		   String[] split = nodes.split(",");
		
            for (String realnode : split) {
				String host=realnode.split(":")[0];
				int port=Integer.parseInt(realnode.split(":")[1]);
				node.add(new HostAndPort(host, port));
				
			}
			return  new JedisCluster(node);
			
		}
}
