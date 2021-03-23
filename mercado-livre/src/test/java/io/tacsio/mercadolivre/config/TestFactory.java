package io.tacsio.mercadolivre.config;

import com.github.javafaker.Faker;
import io.tacsio.mercadolivre.api.request.CategoryRequest;
import io.tacsio.mercadolivre.api.request.UserRequest;
import io.tacsio.mercadolivre.model.Category;
import io.tacsio.mercadolivre.model.data.CategoryRepository;
import io.tacsio.mercadolivre.model.data.ProductRepository;
import io.tacsio.mercadolivre.model.data.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Component
@ActiveProfiles("test")
public class TestFactory {

    private UserRepository userRepository;
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private Faker faker;

    public TestFactory(UserRepository userRepository,
                       ProductRepository productRepository,
                       CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.faker = new Faker();
    }

    @Transactional
    public void cleanDB() {
        productRepository.deleteAll();
        userRepository.deleteAll();
        categoryRepository.deleteAll();
    }


    //requests
    public UserRequest userRequest() {
        var request = new UserRequest();
        request.setLogin(faker.internet().emailAddress());
        request.setPassword(faker.internet().password(6, 10));

        return request;
    }

    public CategoryRequest categoryRequest() {
        var request = new CategoryRequest();
        request.setName(faker.elderScrolls().creature());

        return request;
    }

    public CategoryRequest categoryRequest(long parentId) {
        var request = categoryRequest();
        request.setParentId(parentId);

        return request;
    }

    //entities
    public Category category() {
        var category = new Category(faker.elderScrolls().creature());
        return category;
    }
}
