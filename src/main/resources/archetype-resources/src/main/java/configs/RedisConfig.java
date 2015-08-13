#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

@Configuration
public class RedisConfig {

	@Bean
	public RedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName("localhost");
		factory.setPort(6379);
		return factory;
	}
	
	@Bean
	public RedisTemplate<String, String> jedisTemplate() {
		StringRedisTemplate tmpl = new StringRedisTemplate();
		tmpl.setConnectionFactory(jedisConnectionFactory());
		
		return tmpl;
	}
	
	@Bean
	public RedisScript<Boolean> redisScriptCheckandset() {
		DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<Boolean>();
		redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("/redisscripts/checkandset.lua")));
		redisScript.setResultType(Boolean.class);
		return redisScript;
	}
}
