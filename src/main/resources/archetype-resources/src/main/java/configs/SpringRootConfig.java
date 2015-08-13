#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;

import beans.Person;

@Configuration
@Import(DBConfig.class)
@PropertySource("classpath:authorization.properties")
public class SpringRootConfig {
	@Autowired
	private Environment env;
    
	@Bean
	public static ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasenames("i18n/exceptions");
		return source;
	}
	
	@Bean
	public Person getPerson() {
		System.out.println(env.getProperty("auth.usrs"));
		return new Person();
	}
}
