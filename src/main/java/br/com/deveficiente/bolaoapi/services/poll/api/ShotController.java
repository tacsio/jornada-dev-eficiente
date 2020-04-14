package br.com.deveficiente.bolaoapi.services.poll.api;

import br.com.deveficiente.bolaoapi.services.poll.Shot;
import br.com.deveficiente.bolaoapi.services.poll.api.model.shot.CreateShotRequest;
import br.com.deveficiente.bolaoapi.services.poll.api.model.shot.ShotResponse;
import br.com.deveficiente.bolaoapi.services.user.User;
import br.com.deveficiente.bolaoapi.services.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/shots")
public class ShotController {

    private final EntityManager entityManager;

    //TODO: remove when add spring security features
    private final UserRepository userRepository;

    public ShotController(EntityManager entityManager, UserRepository userRepository) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity createShot(@RequestBody @Valid CreateShotRequest request) {
        //TODO: remove when add spring security features
        User loggedUser = userRepository.findById(1l).get();

        Shot newShot = request.toShot(entityManager, loggedUser);
        entityManager.persist(newShot);

        return ResponseEntity.ok(new ShotResponse(newShot));
    }
}
