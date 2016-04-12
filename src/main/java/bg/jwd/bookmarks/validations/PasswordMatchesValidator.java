package bg.jwd.bookmarks.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import bg.jwd.bookmarks.dto.RegisterFormDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object>{

	@Override
	public void initialize(PasswordMatches constraintAnnotation) { }

	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		RegisterFormDto user = (RegisterFormDto)obj;
		
		return user.getPassword().equals(user.getMatchingPassword());
	}
}
