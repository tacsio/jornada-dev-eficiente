package br.com.deveficiente.bolaoapi.shared.validator;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintViolationCreationContext;
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
public class UniqueValidator implements ConstraintValidator<Unique, String> {

    private EntityManager entityManager;
    private Class entityClass;


    public UniqueValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void initialize(Unique constraintAnnotation) {
        entityClass = constraintAnnotation.entityClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String field = getField((ConstraintValidatorContextImpl) context);

        return !this.exists(entityManager, field, value);
    }

    private String getField(ConstraintValidatorContextImpl context) {
        ConstraintValidatorContextImpl c = context;
        ConstraintViolationCreationContext creationContext = c.getConstraintViolationCreationContexts().get(0);

        return creationContext.getPath().asString();
    }

    private boolean exists(EntityManager entityManager, String field, String value) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();

        Root<?> root = criteriaQuery.from(entityClass);
        Predicate predicate = criteriaBuilder.like(root.get(field), value);
        criteriaQuery.select(root).where(predicate);

        Stream stream = entityManager
                .createQuery(criteriaQuery)
                .setMaxResults(1)
                .getResultStream();

        return stream.findFirst().isPresent();
    }
}
