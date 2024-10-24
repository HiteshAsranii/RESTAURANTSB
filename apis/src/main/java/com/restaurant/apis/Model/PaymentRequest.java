package com.restaurant.apis.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentRequest {
    

    @Column(name = "amount")
    private int amount;

    @Column(name = "amountPaid")
    private int amountPaid;

    @Column(name = "callbackMethod")
    private String callbackMethod;

    @Column(name = "callbackUrl")
    private String callbackUrl;

    private int cancelledAt;

    @Column(name = "createdAt")
    private long createdAt;

    @Column(name = "customerName")
    private String customerName; 

    @Column(name = "customerContactNumber")
    private String customerContactNumber;

    @Column(name = "customerEmail")
    private String customerEmail;

    @Column(name = "description")
    private String description;

    @Column(name = "expireBy")
    private long expireBy;

    @Column(name = "expiredAt")
    private int expiredAt;

    @Column(name = "firstMinPartialAmount")
    private long firstMinPartialAmount;

    @Column(name = "id")
    @Id
    private String id;

    @Column(name = "notes")
    private String notes; 

    @Column(name = "notifyEmail")
    private boolean notifyEmail;

    @Column(name = "notifySms")
    private boolean notifySms;

    @Column(name = "reference_id")
    private String referenceId;

   


}
