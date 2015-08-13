#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package tests;

import java.util.Collections;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import configs.RedisConfig;

public class RedisTests {
	private static AnnotationConfigApplicationContext context;
	
	@Test
	public void redis() {
		RedisTemplate<String, String> bean = context.getBean(RedisTemplate.class);
		bean.opsForValue().set("hello", "world!");
		RedisScript<Boolean> script = context.getBean("redisScriptCheckandset", RedisScript.class);
		Boolean execute = bean.execute(script, Collections.singletonList("key"), "asdf1");
		System.out.println(execute);
	}
	
	@BeforeClass
	public static void init() {
		context = new AnnotationConfigApplicationContext(RedisConfig.class);
		RedisTemplate<String, String> bean = context.getBean(RedisTemplate.class);
		System.out.println(bean.opsForValue().get("hello"));
	}
}
