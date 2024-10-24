package com.restaurant.apis.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WebhookPayload {
    @JsonProperty("account_id")
    private String accountId;
    private List<String> contains;
    @JsonProperty("created_at")
    private long createdAt;
    private String entity;
    private String event;
    private Payload payload;

    @Data
    public static class Payload {
        private Payment payment;
    }

    @Data
    public static class Payment {
        private Entity entity;
    }

    @Data
    public static class Entity {
        @JsonProperty("id")
        private String id;
        @JsonProperty("entity")
        @JsonIgnore // Ignore this field
        private String entity;
        @JsonProperty("acquirer_data")
        private AcquirerData acquirerData;
        private int amount;
        @JsonProperty("amount_refunded")
        private int amountRefunded;
        @JsonProperty("amount_transferred")
        private int amountTransferred;
        private String bank;
        private boolean captured;
        private Card card;
        @JsonProperty("card_id")
        private String cardId;
        private String contact;
        @JsonProperty("created_at")
        private long createdAt;
        private String currency;
        private String description;
        private String email;
        @JsonProperty("error_code")
        private String errorCode;
        @JsonProperty("error_description")
        private String errorDescription;
        @JsonProperty("error_reason")
        private String errorReason;
        @JsonProperty("error_source")
        private String errorSource;
        @JsonProperty("error_step")
        private String errorStep;
        private Object fee;
        private boolean international;
        @JsonProperty("invoice_id")
        private String invoiceId;
        private String method;
        private Notes notes;
        @JsonProperty("order_id")
        private String orderId;
        @JsonProperty("refund_status")
        private Object refundStatus;
        private String status;
        private Object tax;
        @JsonProperty("token_id")
        private String tokenId;
        private Object vpa;
        private Object wallet;
        @JsonProperty("reward")
        private String reward;
        @JsonProperty("base_amount")
        private int baseAmount;

    }

    @Data
    public static class Notes {
        @JsonProperty("OrderId")
        private String restaurantOrderId;
    }

    @Data
    public static class AcquirerData {
        @JsonProperty("auth_code")
        private String authCode;
        private String rrn;
    }

    @Data
    public static class Card {
        private boolean emi;
        private String entity;
        private String id;
        private String iin;
        private boolean international;
        private String issuer;
        private String last4;
        private String name;
        private String network;
        @JsonProperty("sub_type")
        private String subType;
        private String type;
        @JsonProperty("token_iin")
        private String tokenIin;
    }
}
