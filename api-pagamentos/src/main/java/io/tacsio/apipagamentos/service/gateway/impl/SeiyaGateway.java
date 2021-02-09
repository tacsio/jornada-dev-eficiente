package io.tacsio.apipagamentos.service.gateway.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.tacsio.apipagamentos.domain.PaymentMethod;
import io.tacsio.apipagamentos.service.gateway.CardInfo;
import io.tacsio.apipagamentos.service.gateway.Gateway;
import io.tacsio.apipagamentos.service.gateway.GatewayResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Set;

@Service
public class SeiyaGateway implements Gateway {

    @Value("${service.gateway.seiya-auth.uri}")
    private String gatewayAuthURI;

    @Value("${service.gateway.seiya.uri}")
    private String gatewayURI;

    private RestTemplate restTemplate;

    private final BigDecimal tax = BigDecimal.valueOf(6.0);

    private final Set<PaymentMethod.Brand> acceptedBrands = Set.of(PaymentMethod.Brand.values());

    public SeiyaGateway(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean accept(CardInfo cardInfo) {
        return acceptedBrands.contains(cardInfo.brand);
    }

    @Override
    public BigDecimal cost(BigDecimal value) {
        return tax;
    }

    @Override
    public GatewayResponse process(CardInfo cardInfo, BigDecimal value) {
        var authResponse = validateCard(cardInfo);
        if (authResponse.getStatusCode().isError() || !authResponse.getBody().valid()) {
            return GatewayResponse.failed();
        }

        var response = processPayment(authResponse.getBody().id, cardInfo, value);
        var gatewayResponse = response.getBody().gatewayResponse();
        if (response.getStatusCode().isError() || !gatewayResponse.success) {
            return GatewayResponse.failed();
        }

        return gatewayResponse;
    }

    public ResponseEntity<SeiyaAuthResponse> validateCard(CardInfo cardInfo) {
        var authRequest = new HttpEntity<>(new SeiyaAuthRequest(cardInfo));
        var authResponse = restTemplate.postForEntity(gatewayAuthURI, authRequest, SeiyaAuthResponse.class);

        return authResponse;
    }

    public ResponseEntity<SeiyaResponse> processPayment(String authId, CardInfo cardInfo, BigDecimal value) {
        var request = new HttpEntity<>(new SeiyaRequest(authId, cardInfo.cardNumber, cardInfo.securityCode, value));
        var response = restTemplate.postForEntity(gatewayURI, request, SeiyaResponse.class);

        return response;
    }
}

class SeiyaAuthRequest {
    @JsonProperty("num_cartao")
    public final String cardNumber;
    @JsonProperty("codigo_seguranca")
    public final String securityCode;

    public SeiyaAuthRequest(CardInfo cardInfo) {
        this.cardNumber = cardInfo.cardNumber;
        this.securityCode = cardInfo.securityCode;
    }
}

class SeiyaAuthResponse {
    public String id;
    public String status;

    boolean valid() {
        return status.contains("success");
    }
}

class SeiyaRequest {
    public final String id;
    @JsonProperty("num_cartao")
    public final String cardNumber;
    @JsonProperty("codigo_seguranca")
    public final String securityCode;
    @JsonProperty("valor_compra")
    public final BigDecimal value;

    public SeiyaRequest(String id, String cardNumber, String securityCode, BigDecimal value) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.securityCode = securityCode;
        this.value = value;
    }
}

class SeiyaResponse {
    public String authentication;
    public String status;

    GatewayResponse gatewayResponse() {
        return new GatewayResponse(authentication, status.contains("valid"));
    }
}
