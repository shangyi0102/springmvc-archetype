#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package tests;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import configs.DBConfig;
import daos.PersonDao;

public class MysqlTests {
	private static AnnotationConfigApplicationContext context;
	
	
//	@Test
	public void test() {
		PersonDao bean = context.getBean(PersonDao.class);
		System.out.println(new Date());
		for (int i = 0; i < 1000000; ++i) {
			bean.insertUUID();
		}
		System.out.println(new Date());
	}
	
	@BeforeClass
	public static void init() {
		context = new AnnotationConfigApplicationContext(DBConfig.class);
	}
}
