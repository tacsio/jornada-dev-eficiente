package io.tacsio.apipagamentos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.tacsio.apipagamentos.domain.PaymentMethod;
import io.tacsio.apipagamentos.domain.Restaurant;
import io.tacsio.apipagamentos.domain.User;
import io.tacsio.apipagamentos.service.gateway.CardInfo;
import io.tacsio.apipagamentos.service.order.Order;
import io.tacsio.apipagamentos.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static io.tacsio.apipagamentos.domain.PaymentType.*;

@SpringBootApplication
@EnableFeignClients
public class ApiPagamentosApplication {

    @Bean
    CommandLineRunner runner(EntityManager em, TransactionTemplate tx) {
        return args -> {
            //create payment methods
            PaymentMethod visa = new PaymentMethod(CARD, "Visa Card", Optional.of(PaymentMethod.Brand.VISA));
            PaymentMethod master = new PaymentMethod(CARD, "Master Card", Optional.of(PaymentMethod.Brand.MASTERCARD));
            PaymentMethod money = new PaymentMethod(MONEY, "Money", Optional.empty());
            PaymentMethod cardMachine = new PaymentMethod(CARD_MACHINE, "Credit Card Machine", Optional.empty());
            PaymentMethod check = new PaymentMethod(CHECK, "Check", Optional.empty());

            tx.executeWithoutResult(t -> Arrays.asList(visa, master, money, cardMachine, check).forEach(em::persist));

            //create restaurants
            Restaurant outback = new Restaurant("Outback Steakhouse", Arrays.asList(visa, master, money, cardMachine));
            Restaurant eki = new Restaurant("EKI", Arrays.asList(visa, master, cardMachine));
            Restaurant boteco = new Restaurant("Boteco Do Cordel", Arrays.asList(money, check));
            Restaurant debtor = new Restaurant("Sr. dos Calotes", Arrays.asList(master, visa, money, check, cardMachine));

            tx.executeWithoutResult(t -> Arrays.asList(outback, eki, boteco, debtor).forEach(em::persist));

            //create users
            User tacsio = new User("tacsio@deveficiente.com", Arrays.asList(master, cardMachine));
            User alberto = new User("alberto@deveficiente.com", Arrays.asList(visa, money, check, cardMachine));
            User gustavo = new User("gustavo@deveficiente.com", Arrays.asList(master, visa, money, cardMachine));
            User fraudster = new User("fraudster@frauders.com", Arrays.asList(master, visa, money, check, cardMachine));

            tx.executeWithoutResult(t -> Arrays.asList(tacsio, alberto, gustavo, fraudster).forEach(em::persist));
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiPagamentosApplication.class, args);
    }
}

@RestController
class TestController {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    OrderService service;

    @GetMapping("/feign/{id}")
    public ResponseEntity feign(@PathVariable Long id) {
        Order order = service.getOrder(id);
        return ResponseEntity.ok(order);
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

    //GATEWAYS DUMMY
    @PostMapping("/jornada-dev-eficiente/tango")
    public ResponseEntity tangoGateway(@Valid @RequestBody TangoRequest request) throws InterruptedException {
        var fail = Math.random() * 100 % 2 == 0;
        var slow = Math.random() * 100 % 2 == 0;
        if (fail) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        if (slow) TimeUnit.SECONDS.sleep(30);

        var response = Map.of("id", UUID.randomUUID().toString(), "ok", true);
        return ResponseEntity.ok(response);
    }

    class TangoRequest {
        @NotBlank
        @JsonProperty("numero_cartao")
        public String cardNumber;
        @NotBlank
        @JsonProperty("codigo_seguranca")
        public String securityCode;
        @JsonProperty("valor_compra")
        public BigDecimal value;

        TangoRequest(CardInfo cardInfo, BigDecimal value) {
            this.cardNumber = cardInfo.cardNumber;
            this.securityCode = cardInfo.securityCode;
            this.value = value;
        }
    }

    @PostMapping("/jornada-dev-eficiente/saori")
    public ResponseEntity saioriGateway(@Valid @RequestBody SaoriRequest request) {
        var fail = Math.random() * 100 % 3 == 0;
        if (fail) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        var response = Map.of("authorization", UUID.randomUUID().toString(), "processed", true);
        return ResponseEntity.ok(response);
    }

    class SaoriRequest {
        @NotBlank
        @JsonProperty("num_cartao")
        public String cardNumber;
        @NotBlank
        @JsonProperty("codigo_seguranca")
        public String securityCode;
        @JsonProperty("valor_compra")
        public BigDecimal value;

        SaoriRequest(CardInfo cardInfo, BigDecimal value) {
            this.cardNumber = cardInfo.cardNumber;
            this.securityCode = cardInfo.securityCode;
            this.value = value;
        }
    }

    @PostMapping("/jornada-dev-eficiente/seiya/auth")
    public ResponseEntity seiyaAuthGateway(@Valid @RequestBody SeiyaAuthRequest request) {
        var fail = Math.random() * 100 % 2 == 0;
        if (fail) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        var response = Map.of("id", UUID.randomUUID().toString(), "status", "success");
        return ResponseEntity.ok(response);
    }

    class SeiyaAuthRequest {
        @NotBlank
        public String num_cartao;
        @NotBlank
        public String codigo_seguranca;

        public SeiyaAuthRequest(String num_cartao, String codigo_seguranca) {
            this.num_cartao = num_cartao;
            this.codigo_seguranca = codigo_seguranca;
        }
    }

    @PostMapping("/jornada-dev-eficiente/seiya")
    public ResponseEntity seiyaGateway(@Valid @RequestBody SeiyaRequest request) {
        var fail = Math.random() * 100 % 2 == 0;
        var notFound = Math.random() * 100 % 3 == 0;
        if (fail) throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        if (notFound) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        var response = Map.of("authentication", request.id, "status", "valid");
        return ResponseEntity.ok(response);
    }

    class SeiyaRequest {
        @NotBlank
        public String id;
        public BigDecimal valor_compra;

        public SeiyaRequest(String id, BigDecimal valor_compra) {
            this.id = id;
            this.valor_compra = valor_compra;
        }
    }
}