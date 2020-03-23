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
public class UniqueValidator implements ConstraintValidator<Unique, String> {

    private EntityManager entityManager;
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
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !this.exists(entityManager, entityClass, entityField, value);
    }

    private boolean exists(EntityManager entityManager, Class entityClass, String field, String fieldValue) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();

        Root<?> root = criteriaQuery.from(entityClass);
        Predicate predicate = criteriaBuilder.like(root.get(field), fieldValue);
        criteriaQuery.select(root).where(predicate);

        Stream stream = entityManager
                .createQuery(criteriaQuery)
                .setMaxResults(1)
                .getResultStream();

        return stream.findFirst().isPresent();
    }
}
