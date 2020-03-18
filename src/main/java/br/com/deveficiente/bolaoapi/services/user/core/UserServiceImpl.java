package br.com.deveficiente.bolaoapi.services.user.core;

import br.com.deveficiente.bolaoapi.services.user.core.model.UserRepository;
import br.com.deveficiente.bolaoapi.services.user.UserService;
import br.com.deveficiente.bolaoapi.services.user.core.model.User;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.validation.ConstraintViolationException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User create(User user) {

        String hashedPassword = hashPassword(user.getPassword());
        user.setPassword(hashedPassword);

        return this.userRepository.save(user);
    }


    @SneakyThrows
    private String hashPassword(String rawPassword) {
        final int saltSize = 32;
        final int keyIteration = 10000;
        final int keyLength = 256;

        byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltSize);

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(rawPassword.toCharArray(), salt, keyIteration, keyLength);
        SecretKey key = factory.generateSecret(spec);
        String hash = Base64Utils.encodeToString(key.getEncoded());

        byte[] payload = (salt + "$" + hash).getBytes();

        return Base64Utils.encodeToString(payload);
    }

}
