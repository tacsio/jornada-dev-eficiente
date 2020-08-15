package io.tacsio.apipagamentos.api;

import io.tacsio.apipagamentos.domain.PaymentMethod;
import io.tacsio.apipagamentos.domain.Restaurant;
import io.tacsio.apipagamentos.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.Optional;

import static io.tacsio.apipagamentos.domain.PaymentType.*;

@RestController
public class TestController {

    @PersistenceContext
    private EntityManager em;

    @GetMapping("/store")
    @Transactional
    public ResponseEntity store() {
        //create payment methods
        PaymentMethod visa = new PaymentMethod(CARD, "Visa Card", Optional.of(PaymentMethod.Brand.VISA));
        PaymentMethod master = new PaymentMethod(CARD, "Master Card", Optional.of(PaymentMethod.Brand.MASTERCARD));
        PaymentMethod money = new PaymentMethod(MONEY, "Money", Optional.empty());
        PaymentMethod cardMachine = new PaymentMethod(CARD_MACHINE, "Credit Card Machine", Optional.empty());
        PaymentMethod check = new PaymentMethod(CHECK, "Check", Optional.empty());

        Arrays.asList(visa, master, money, cardMachine, check).forEach(em::persist);

        //create restaurants
        Restaurant outback = new Restaurant("Outback Steakhouse", Arrays.asList(visa, master, money, cardMachine));
        Restaurant eki = new Restaurant("EKI", Arrays.asList(visa, master, cardMachine));
        Restaurant boteco = new Restaurant("Boteco Do Cordel", Arrays.asList(money, check));
        Arrays.asList(outback, eki, boteco).forEach(em::persist);

        //create users
        User tacsio = new User("tacsio@mail.com", Arrays.asList(master, cardMachine));
        User alberto = new User("alberto@deveficiente.com", Arrays.asList(visa, money, check, cardMachine));
        User gustavo = new User("gustavo@deveficiente.com", Arrays.asList(master, visa, money, cardMachine));
        Arrays.asList(tacsio, alberto, gustavo).forEach(em::persist);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{id}")
    public User user(@PathVariable Long id) {
        return em.find(User.class, id);
    }

    @GetMapping("/playments/{id}")
    public PaymentMethod paymentMethod(@PathVariable Long id) {
        return em.find(PaymentMethod.class, id);
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant restaurant(@PathVariable Long id) {
        return em.find(Restaurant.class, id);
    }

}
