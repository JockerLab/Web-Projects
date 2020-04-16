package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.TagsCredentials;
import ru.itmo.wp.service.TagService;

@Component
public class TagValidator implements Validator {
    public boolean supports(Class<?> clazz) {
        return TagsCredentials.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            TagsCredentials tagsList = (TagsCredentials) target;
            if (!tagsList.getName().matches("[\\sa-zA-Z]+")) {
                errors.rejectValue("name", "name.invalid-login-or-password", "tags must be latin letters");
            }
        }
    }
}
