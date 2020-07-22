package io.tacsio.validators;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.stream.Stream;

@ApplicationScoped
public class UniqueValidator implements ConstraintValidator<Unique, Object> {

	@Inject
	private EntityManager entityManager;
	private Class entityClass;
	private String entityField;

	@Override
	public void initialize(Unique constraintAnnotation) {
		entityClass = constraintAnnotation.entityClass();
		entityField = constraintAnnotation.entityField();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		return !exists(entityManager, entityClass, entityField, value);
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
