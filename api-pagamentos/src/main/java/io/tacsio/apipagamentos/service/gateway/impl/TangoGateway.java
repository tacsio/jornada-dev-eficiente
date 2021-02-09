package io.tacsio.apipagamentos.service.gateway.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.tacsio.apipagamentos.domain.PaymentMethod;
import io.tacsio.apipagamentos.service.gateway.CardInfo;
import io.tacsio.apipagamentos.service.gateway.Gateway;
import io.tacsio.apipagamentos.service.gateway.GatewayResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Set;

@Service
public class TangoGateway implements Gateway {

    @Value("${service.gateway.tango.uri}")
    private String gatewayURI;

    private RestTemplate restTemplate;

    private final BigDecimal variableTax = BigDecimal.valueOf(0.06);
    private final BigDecimal fixedTax = BigDecimal.valueOf(4.0);

    private final Set<PaymentMethod.Brand> acceptedBrands = Set.of(PaymentMethod.Brand.values());

    public TangoGateway(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean accept(CardInfo cardInfo) {
        return acceptedBrands.contains(cardInfo.brand);
    }

    @Override
    public BigDecimal cost(BigDecimal value) {
        BigDecimal cost = fixedTax;

        if (value.compareTo(BigDecimal.valueOf(100.0)) > 0) {
            cost = value.multiply(variableTax);
        }

        return cost;
    }

    @Override
    public GatewayResponse process(CardInfo cardInfo, BigDecimal value) {
        var request = new HttpEntity<>(new TangoRequest(cardInfo, value));
        var response = restTemplate.exchange(gatewayURI, HttpMethod.POST, request, TangoResponse.class);

        var gatewayResponse = response.getBody().gatewayResponse();
        if (response.getStatusCode().isError() || !gatewayResponse.success) {
            return GatewayResponse.failed();
        }

        return gatewayResponse;
    }
}

class TangoRequest {
    @JsonProperty("numero_cartao")
    public final String cardNumber;
    @JsonProperty("codigo_seguranca")
    public final String securityCode;
    @JsonProperty("valor_compra")
    public final BigDecimal value;

    TangoRequest(CardInfo cardInfo, BigDecimal value) {
        this.cardNumber = cardInfo.cardNumber;
        this.securityCode = cardInfo.securityCode;
        this.value = value;
    }
}

class TangoResponse {
    public String id;
    public boolean ok;

    GatewayResponse gatewayResponse() {
        return new GatewayResponse(id, ok);
    }

}