package com.restaurant.apis.service;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.razorpay.RazorpayException;
import com.restaurant.apis.model.PaymentRequest;

import jakarta.transaction.Transactional;

@Service
@Transactional
public interface PaymentService {
    public String createPaymentLink(PaymentRequest paymentRequest) throws RazorpayException;
    public ResponseEntity<Object> verifyPayment(String requestBody, String Signature) throws JsonMappingException, JsonProcessingException;
    public boolean isValidRequest(String requestBody,String signature);
}