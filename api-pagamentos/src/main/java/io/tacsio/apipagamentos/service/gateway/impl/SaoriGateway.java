package io.tacsio.apipagamentos.service.gateway.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.tacsio.apipagamentos.domain.PaymentMethod;
import io.tacsio.apipagamentos.service.gateway.CardInfo;
import io.tacsio.apipagamentos.service.gateway.Gateway;
import io.tacsio.apipagamentos.service.gateway.GatewayResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Set;

@Service
public class SaoriGateway implements Gateway {

    @Value("${service.gateway.saori.uri}")
    private String gatewayURI;

    private RestTemplate restTemplate;

    private final BigDecimal tax = BigDecimal.valueOf(0.05);

    private final Set<PaymentMethod.Brand> acceptedBrands = Set.of(
        PaymentMethod.Brand.VISA, PaymentMethod.Brand.MASTERCARD);

    public SaoriGateway(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean accept(CardInfo cardInfo) {
        return acceptedBrands.contains(cardInfo.brand);
    }

    @Override
    public BigDecimal cost(BigDecimal value) {
        return value.multiply(tax);
    }

    @Override
    public GatewayResponse process(CardInfo cardInfo, BigDecimal value) {
        var request = new HttpEntity<>(new SaoriRequest(cardInfo, value));
        var response = restTemplate.postForEntity(gatewayURI, request, SaoriResponse.class);

        var gatewayResponse = response.getBody().gatewayResponse();
        if (response.getStatusCode().isError() || !gatewayResponse.success) {
            return GatewayResponse.failed();
        }

        return gatewayResponse;
    }
}

class SaoriRequest {
    @JsonProperty("num_cartao")
    public final String cardNumber;
    @JsonProperty("codigo_seguranca")
    public final String securityCode;
    @JsonProperty("valor_compra")
    public final BigDecimal value;

    SaoriRequest(CardInfo cardInfo, BigDecimal value) {
        this.cardNumber = cardInfo.cardNumber;
        this.securityCode = cardInfo.securityCode;
        this.value = value;
    }
}

class SaoriResponse {
    public String authorization;
    public boolean processed;

    GatewayResponse gatewayResponse() {
        return new GatewayResponse(authorization, processed);
    }
}
