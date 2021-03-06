package io.tacsio.mercadolivre.api;

import io.tacsio.mercadolivre.api.representer.CategoryRepresenter;
import io.tacsio.mercadolivre.api.request.CategoryRequest;
import io.tacsio.mercadolivre.data.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @PostMapping("/categories")
    public ResponseEntity<?> create(@Valid @RequestBody CategoryRequest request) {

        var category = request.toModel(categoryRepository);
        categoryRepository.save(category);

        return ResponseEntity.ok(new CategoryRepresenter(category));
    }
}
