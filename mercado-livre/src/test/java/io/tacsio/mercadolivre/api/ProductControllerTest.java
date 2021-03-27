package io.tacsio.mercadolivre.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.tacsio.mercadolivre.config.TestFactory;
import io.tacsio.mercadolivre.data.CategoryRepository;
import io.tacsio.mercadolivre.data.ProductRepository;
import io.tacsio.mercadolivre.data.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestFactory testFactory;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setup() {
        testFactory.cleanDB();
    }

    @Test
    @DisplayName("Create product.")
    void create() {

    }
}