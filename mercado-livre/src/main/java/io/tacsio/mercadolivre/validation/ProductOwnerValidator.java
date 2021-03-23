package io.tacsio.mercadolivre.validation;

import io.tacsio.mercadolivre.model.User;
import io.tacsio.mercadolivre.model.data.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Service
public class ProductOwnerValidator implements ConstraintValidator<ProductOwner, Long> {

    private ProductRepository productRepository;

    public ProductOwnerValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean isValid(Long productId, ConstraintValidatorContext context) {
        var product = productRepository.findById(productId);

        //if not exits belongs to anyone
        if (product.isEmpty()) return true;

        var loggedUser = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        var isOwner = product.get().getOwner().equals(loggedUser);
        if (!isOwner) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, context.getDefaultConstraintMessageTemplate());
        }

        return true;
    }
}
