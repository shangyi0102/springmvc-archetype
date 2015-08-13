#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import beans.Person;

public class PersonValidator implements Validator {
	private Object[] validateArgs = new Object[]{"name"};
	
	@Override
	public boolean supports(Class<?> arg0) {
		return Person.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object obj, Errors e) {
		 ValidationUtils.rejectIfEmpty(e, "name", "argument.required", validateArgs);
	     Person p = (Person) obj;

	     if (p.getAge() < 0) {
	        e.rejectValue("age", "negativevalue");
	     } else if (p.getAge() > 110) {
	    	e.rejectValue("age", "too.darn.old");
	     }
	}

}
