package br.com.deveficiente.bolaoapi.services.user.core;

import br.com.deveficiente.bolaoapi.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public User create(User user) {

        String hashedPassword = encoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        return this.userRepository.save(user);
    }
}
