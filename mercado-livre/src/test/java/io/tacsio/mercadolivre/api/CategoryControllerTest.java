package io.tacsio.mercadolivre.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.tacsio.mercadolivre.api.request.CategoryRequest;
import io.tacsio.mercadolivre.config.TestFactory;
import io.tacsio.mercadolivre.model.Category;
import io.tacsio.mercadolivre.model.data.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TestFactory testFactory;

    @BeforeEach
    void setup() {
        testFactory.cleanDB();
    }


    @Test
    @DisplayName("Simple category creation.")
    @WithMockUser(username = "testUser", authorities = "category:write")
    public void createCategory() throws Exception {
        CategoryRequest request = testFactory.categoryRequest();

        var payload = mapper.writeValueAsString(request);

        mvc.perform(
                MockMvcRequestBuilders.post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(request.getName()));
    }

    @Test
    @DisplayName("Nested category creation.")
    @WithMockUser(username = "testUser", authorities = "category:write")
    public void createNestedCategories() throws Exception {
        Category parent = testFactory.category();
        categoryRepository.save(parent);

        var child = testFactory.categoryRequest(parent.getId());
        var payload = mapper.writeValueAsString(child);

        mvc.perform(
                MockMvcRequestBuilders.post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.parent.name").value(parent.getName()));
    }

    @Test
    @DisplayName("Should not permit 2 categories with the same name.")
    @WithMockUser(username = "testUser", authorities = "category:write")
    public void duplicatedCategory() throws Exception {
        Category existent = testFactory.category();
        categoryRepository.save(existent);

        CategoryRequest request = new CategoryRequest();
        request.setName(existent.getName());

        var payload = mapper.writeValueAsString(request);

        mvc.perform(
                MockMvcRequestBuilders.post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Should not accept categories without name.")
    @WithMockUser(username = "testUser", authorities = "category:write")
    public void categoryWithoutName() throws Exception {
        CategoryRequest request = new CategoryRequest();

        var payload = mapper.writeValueAsString(request);

        mvc.perform(
                MockMvcRequestBuilders.post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Should not access without authentication.")
    public void accessWithoutLogin() throws Exception {
        CategoryRequest request = testFactory.categoryRequest();

        var payload = mapper.writeValueAsString(request);

        mvc.perform(
                MockMvcRequestBuilders.post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @DisplayName("Should not access without specific permission to write.")
    @WithMockUser(username = "testUser", authorities = "category:read")
    public void accessWithoutPermission() throws Exception {
        CategoryRequest request = testFactory.categoryRequest();

        var payload = mapper.writeValueAsString(request);

        mvc.perform(
                MockMvcRequestBuilders.post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}