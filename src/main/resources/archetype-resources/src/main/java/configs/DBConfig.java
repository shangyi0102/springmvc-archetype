#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package configs;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import daos.PersonDao;
import daos.PersonDaoImpl;

@Configuration
@PropertySource("classpath:jdbc-${symbol_dollar}{spring.profiles.active:production}.properties")
public class DBConfig {
	@Autowired
	private Environment env;
	
	@Bean
	public DataSource getDataSource() {
		String driverClassName = env.getProperty("jdbc.driverClassName");
		String url = env.getProperty("jdbc.url");
		String username = env.getProperty("jdbc.username");
		String password = env.getProperty("jdbc.password");
		System.out.println("db : " + url + username + password);
		
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(driverClassName);
		basicDataSource.setUrl(url);
		basicDataSource.setUsername(username);
		basicDataSource.setPassword(password);
		return basicDataSource;
	}
	
	@Bean
	public PersonDao getAppointmentDao() {
		PersonDaoImpl impl = new PersonDaoImpl();
		impl.setDataSource(getDataSource());
		return impl;
	}
	

}
