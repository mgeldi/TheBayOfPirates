package de.htwberlin.de.TheBayOfPirates;

import de.htwberlin.de.TheBayOfPirates.registration.model.User;
import de.htwberlin.de.TheBayOfPirates.registration.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "Name", "NotEmpty");
        if (user.getName().length() < 3 || user.getName().length() > 24){
            errors.rejectValue("Name","Size.userForm.name");
        }

        if (user.getName().isEmpty()){
            errors.rejectValue("Name","IsEmpty");
        }

        if (Character.isUpperCase(user.getName().codePointAt(0)) == false){
            errors.rejectValue("Name","FirstLetterCapital");
        }








    }


}
