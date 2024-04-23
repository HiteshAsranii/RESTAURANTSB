package com.restaurant.apis.Service;


import org.springframework.stereotype.Service;

import com.razorpay.RazorpayException;
import com.restaurant.apis.Model.PaymentRequest;

import jakarta.transaction.Transactional;

@Service
@Transactional
public interface PaymentService {
    public String createPaymentLink(PaymentRequest paymentRequest) throws RazorpayException;
}