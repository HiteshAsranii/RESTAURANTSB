package com.restaurant.apis.Controller;

import org.hibernate.boot.beanvalidation.IntegrationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.razorpay.RazorpayException;
import com.restaurant.apis.Model.PaymentRequest;
import com.restaurant.apis.Service.PaymentService;
import com.restaurant.apis.Service.RestaurantTableService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("payments")
@CrossOrigin(origins = "http://localhost:5173/")
public class PaymentController {
    private final PaymentService paymentService;


    final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    public PaymentController(PaymentService paymentService, RestaurantTableService restaurantTableService) {
        super();
        this.paymentService = paymentService;
   
    }

    @PostMapping("/createPaymentLink")
    public String createPaymentLink(@RequestBody PaymentRequest paymentRequest) throws RazorpayException {
        return paymentService.createPaymentLink(paymentRequest);
    }

    @PostMapping("/verification")
    public ResponseEntity<Object> verifyPayment(@RequestBody String requestBody,
            @RequestHeader("X-Razorpay-Signature") String signature)
            throws JsonMappingException, JsonProcessingException {
        return paymentService.verifyPayment(requestBody, signature);
    }

    

}
