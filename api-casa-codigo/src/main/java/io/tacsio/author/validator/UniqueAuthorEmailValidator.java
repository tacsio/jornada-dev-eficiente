package io.tacsio.author.validator;

import io.tacsio.author.Autor;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * With ApplicationScoped context, the validation doesn't work as the way as I imagined
 * (and works on Spring https://github.com/tacsio/jornada-dev-eficiente/blob/master/bolao-api/src/main/java/br/com/deveficiente/bolaoapi/shared/validator/UniqueValidator.java)
 * <p>
 * The real good scope here could be RequestScoped (the constraint annotation would be initialized
 * every validation).
 * <p>
 * There is a Quarkus issue related https://github.com/quarkusio/quarkus/issues/6094
 * This approach only works with ApplicationScoped.
 * <p>
 * So I've to create specific class annotations T_T
 */
@ApplicationScoped
public class UniqueAuthorEmailValidator implements ConstraintValidator<UniqueAuthorEmail, String> {

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		return Autor.find("email", email).firstResultOptional().isEmpty();
	}
}
