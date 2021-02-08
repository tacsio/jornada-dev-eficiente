package io.tacsio.apipagamentos.service.gateway;

public class GatewayResponse {
    public final String id;
    public final boolean success;

    public GatewayResponse(String id, boolean success) {
        this.id = id;
        this.success = success;
    }

    public static GatewayResponse failed() {
        return new GatewayResponse(null, false);
    }
}
