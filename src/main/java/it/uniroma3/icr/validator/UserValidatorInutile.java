package it.uniroma3.icr.validator;

import it.uniroma3.icr.model.Student;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidatorInutile implements Validator {

	@Override
	public void validate(Object o, Errors e) {
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "name", "Non deve essere vuoto");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "surname", "Non deve essere vuoto");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "school", "Non deve essere vuoto");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "section", "Non deve essere vuoto");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "username", "Non deve essere vuoto");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "password", "Non deve essere vuoto");
		
		
		Student student= (Student) o;
		if(student.getName()==null){
			e.rejectValue("name", "vuoto");
		}

	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Student.class.equals(clazz);
	}

}



