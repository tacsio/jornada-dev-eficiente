package io.tacsio.apipagamentos.service.gateway;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GatewaySelector {
    private Set<Gateway> gateways;

    public GatewaySelector(Set<Gateway> gateways) {
        this.gateways = gateways;
    }

    public Queue<Gateway> availableGateways(CardInfo cardInfo, BigDecimal value) {

        var sortedGateways = gateways.stream()
            .filter(gateway -> gateway.accept(cardInfo))
            .sorted(Comparator.comparing(gateway -> gateway.cost(value)))
            .collect(Collectors.toCollection(ArrayDeque::new));

        return sortedGateways;
    }

}
