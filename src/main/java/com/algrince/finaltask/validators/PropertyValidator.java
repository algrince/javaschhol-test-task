package com.algrince.finaltask.validators;

import com.algrince.finaltask.models.Property;
import com.algrince.finaltask.services.PropertiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

/**
 * Additional server-side validation for properties. Prevents
 * saving to the database of two properties with the same name
 */

@Component
@RequiredArgsConstructor
public class PropertyValidator implements Validator {

    private final PropertiesService propertiesService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Property.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Property property = (Property) target;
        Optional<Property> propertyFromDB =
                propertiesService.loadByName(property.getName());

        if (propertyFromDB.isPresent()) {
            errors.rejectValue(
                    "name",
                    "property.name.exists",
                    "The property with this name already exists");
        }
    }
}
