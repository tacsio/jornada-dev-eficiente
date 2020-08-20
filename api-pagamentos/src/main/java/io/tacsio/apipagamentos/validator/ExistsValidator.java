package io.tacsio.apipagamentos.validator;


import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.stream.Stream;

public class ExistsValidator implements ConstraintValidator<Exists, Long> {

    private final EntityManager entityManager;
    private Class entityClass;
    private String entityField;

    public ExistsValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void initialize(Exists constraintAnnotation) {
        this.entityClass = constraintAnnotation.entityClass();
        this.entityField = constraintAnnotation.entityField();
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        System.out.println("FIRST EXISTS>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return exists(entityManager, entityClass, entityField, value);
    }

    public static boolean exists(EntityManager entityManager, Class entityClass, String field, Object fieldValue) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();

        Root<?> root = criteriaQuery.from(entityClass);
        Predicate predicate = criteriaBuilder.equal(root.get(field), fieldValue);
        criteriaQuery.select(root).where(predicate);

        Stream stream = entityManager
                .createQuery(criteriaQuery)
                .setMaxResults(1)
                .getResultStream();

        return stream.findFirst().isPresent();
    }
}