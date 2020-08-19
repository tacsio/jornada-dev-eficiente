package io.tacsio.apipagamentos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiPagamentosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiPagamentosApplication.class, args);
    }
}
