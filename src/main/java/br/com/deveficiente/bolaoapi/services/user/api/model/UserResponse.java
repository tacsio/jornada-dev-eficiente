package br.com.deveficiente.bolaoapi.services.user.api.model;

import br.com.deveficiente.bolaoapi.services.user.core.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private Date timestamp;
    private String login;

    public static UserResponse fromUser(User user) {
        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .timestamp(user.getTimestamp())
                .login(user.getLogin())
                .build();

        return response;
    }

}
