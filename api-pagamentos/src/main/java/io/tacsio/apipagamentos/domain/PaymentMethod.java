package io.tacsio.apipagamentos.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Entity
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type;

    @NotBlank
    private String description;

    @Enumerated(EnumType.STRING)
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)//TODO: move to DTO
    private CardBrand brand;

    @Deprecated
    protected PaymentMethod() {
    }

    public PaymentMethod(@NotNull Type type, @NotBlank String description, Optional<CardBrand> brand) {
        this.type = type;
        this.description = description;

        if (type.equals(Type.CARD)) {
            Assert.isTrue(brand.isPresent(), "For Card Type brand is mandatory.");
            this.brand = brand.get();
        }
    }

    public Type getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public boolean isOnline() {
        return type.online;
    }

    public boolean isOffline() {
        return type.offline;
    }

    public Optional<CardBrand> getBrand() {
        return Optional.ofNullable(brand);
    }

    public enum Type {
        CARD(true, true),
        MONEY(false, true),
        CARD_MACHINE(false, true),
        CHECK(false, true);

        private final boolean online;
        private final boolean offline;

        Type(boolean online, boolean offline) {
            this.online = online;
            this.offline = offline;
        }

        public boolean acceptOnline() {
            return online;
        }

        public boolean acceptOffline() {
            return offline;
        }
    }

    public enum CardBrand {
        VISA, MASTERCARD, ELO, HYPERCARD
    }
}


