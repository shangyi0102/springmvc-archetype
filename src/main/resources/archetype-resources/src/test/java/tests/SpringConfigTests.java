#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package tests;

import java.util.Locale;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;

import beans.Person;
import configs.SpringRootConfig;
import validators.PersonValidator;

public class SpringConfigTests {
	private static AnnotationConfigApplicationContext context;
	
	@Test
	public void test() {
		String message = context.getMessage("argument.required", new Object[]{"userDao"}, "required", null);
		System.out.println(message);

		String message2 = context.getMessage("argument.required", new Object[]{"userDao"}, "required", Locale.UK);
		System.out.println(message2);
		System.out.println(Locale.CHINA);
		String message3 = context.getMessage("argument.required", new Object[]{"userDao"}, "required", Locale.CHINA);
		System.out.println(message3);
	}
	
	@Test
	public void testValidator() {
		Person person = new Person();
		
		PersonValidator validate = new PersonValidator();
		DataBinder binder = new DataBinder(person, "person");
		binder.setValidator(validate);
		MutablePropertyValues mpv = new MutablePropertyValues();
		mpv.add("name", null);
		mpv.add("age", "a");
		
		binder.bind(mpv);
		binder.validate();
		
		BindingResult bindingResult = binder.getBindingResult();
		if (bindingResult.hasErrors()) {
//			System.out.println(bindingResult.getAllErrors());
			for (ObjectError e : bindingResult.getAllErrors()) {
				System.out.println(e.getCode());
				String msg;
				try {
					msg = context.getMessage(e.getCode(), e.getArguments(), null);
				} catch (NoSuchMessageException error) {
					msg = e.getDefaultMessage();
				}
				System.out.println(msg);
			}
			
		} else {
			System.out.println("success " + person.getName() + " : " + person.getAge());
		}
	}
	
	@BeforeClass
	public static void init() {
		context = new AnnotationConfigApplicationContext(SpringRootConfig.class);
	}
}
