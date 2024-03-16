package com.enigma.sun_florist.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@RequiredArgsConstructor
@Component
public class ValidationUtil {

    private final Validator validator;

    public void validate(Object obj) {
        Set<ConstraintViolation<Object>> validate = validator.validate(obj);
        if (!validate.isEmpty()) throw new ConstraintViolationException(validate);
    }

}
