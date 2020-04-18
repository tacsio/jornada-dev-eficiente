package br.com.deveficiente.bolaoapi.shared.validator;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.stream.Stream;

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
        return !ExistsValidation.exists(entityManager, entityClass, entityField, value);
    }
}
