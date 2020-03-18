package br.com.deveficiente.bolaoapi.services.user.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "poll_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "timestamp should not be null")
    private Date timestamp;

    @NotNull(message = "login should not be null")
    @Email(message = "wrong format login format", regexp = ".+@.+\\..+")
    private String login;

    @NotNull(message = "password should not be null")
    private String password;
}
