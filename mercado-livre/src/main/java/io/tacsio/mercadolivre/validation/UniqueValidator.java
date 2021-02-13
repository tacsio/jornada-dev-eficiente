package io.tacsio.mercadolivre.validation;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Service
public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    private final EntityManager entityManager;
    private Class entityClass;
    private String entityField;

    public UniqueValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void initialize(Unique constraintAnnotation) {
        entityClass = constraintAnnotation.entityClass();
        entityField = constraintAnnotation.entityField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return !ExistsValidator.exists(entityManager, entityClass, entityField, value);
    }
}