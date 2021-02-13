package io.tacsio.mercadolivre.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.tacsio.mercadolivre.api.request.NewUserRequest;
import io.tacsio.mercadolivre.model.data.UserRepository;
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

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("User registration.")
    void createUser() throws Exception {
        NewUserRequest newUser = new NewUserRequest();
        newUser.setLogin("user@mail.com");
        newUser.setPassword("123456");

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
        NewUserRequest newUser = new NewUserRequest();
        newUser.setLogin("user");
        newUser.setPassword("123456");

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
        NewUserRequest newUser = new NewUserRequest();
        newUser.setLogin("user@mail.com");
        newUser.setPassword("123456");

        userRepository.save(newUser.toUser(new BCryptPasswordEncoder()));

        String payload = mapper.writeValueAsString(newUser);


        mvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}