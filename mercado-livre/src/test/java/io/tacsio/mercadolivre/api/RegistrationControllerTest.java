package io.tacsio.mercadolivre.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.tacsio.mercadolivre.api.request.UserRequest;
import io.tacsio.mercadolivre.config.TestFactory;
import io.tacsio.mercadolivre.data.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class RegistrationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestFactory testFactory;

    @BeforeEach
    void setup() {
        testFactory.cleanDB();
    }

    @Test
    @DisplayName("User registration.")
    void createUser() throws Exception {
        UserRequest newUser = testFactory.userRequest();
        String payload = mapper.writeValueAsString(newUser);


        mvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.login").value(newUser.getLogin()));
    }

    @Test
    @DisplayName("User invalid registration.")
    void createInvalidUser() throws Exception {
        UserRequest newUser = testFactory.userRequest();
        newUser.setLogin("invalid login");

        String payload = mapper.writeValueAsString(newUser);


        mvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Already used login.")
    void duplicatedLogin() throws Exception {
        UserRequest newUser = testFactory.userRequest();

        userRepository.save(newUser.toUser(new BCryptPasswordEncoder()));

        String payload = mapper.writeValueAsString(newUser);


        mvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}