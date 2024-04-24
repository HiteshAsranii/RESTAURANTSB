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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.restaurant.apis.Model.Orders;
import com.restaurant.apis.Model.PaymentRequest;
import com.restaurant.apis.Model.Payments;
import com.restaurant.apis.Service.PaymentService;
import com.restaurant.apis.Service.RestaurantTableService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.restaurant.apis.Model.WebhookPayload;

@RestController
@RequestMapping("payments")
@CrossOrigin(origins = "http://localhost:5173/")
public class PaymentController {
    private final PaymentService paymentService;
    private final RestaurantTableService restaurantTableService;

    final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    public PaymentController(PaymentService paymentService, RestaurantTableService restaurantTableService) {
        super();
        this.paymentService = paymentService;
        this.restaurantTableService = restaurantTableService;
    }

    @PostMapping("createPaymentLink")
    public String createPaymentLink(@RequestBody PaymentRequest paymentRequest) throws RazorpayException {
        return paymentService.createPaymentLink(paymentRequest);
    }

    @PostMapping("/verification")
    public ResponseEntity<Object> verifyPayment(@RequestBody String requestBody,
            @RequestHeader("X-Razorpay-Signature") String signature)
            throws JsonMappingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Deserializing it
            WebhookPayload webhookPayload = objectMapper.readValue(requestBody, WebhookPayload.class);
            // Print the deserialized JSON map
            System.out.println(webhookPayload);

            if (isValidRequest(requestBody, signature)) { // Return a success response
                Payments payment = new Payments();
                payment.setPaymentId(webhookPayload.getPayload().getPayment().getEntity().getId());
                payment.setRazorPayId(webhookPayload.getPayload().getPayment().getEntity().getOrderId());
                payment.setAmount((double) (webhookPayload.getPayload().getPayment().getEntity().getAmount() / 100));
                payment.setPaymentMethod(webhookPayload.getPayload().getPayment().getEntity().getCard().getType());
                int restaurantOrderId = Integer.parseInt(
                        webhookPayload.getPayload().getPayment().getEntity().getNotes().getRestaurantOrderId());
                Orders order = new Orders();
                order.setOrderId(restaurantOrderId);
                payment.setOrderId(order);
                System.out.println("payment entity---->" + payment);
                System.out.println("Unreserving table");
                restaurantTableService.unReserveTable(restaurantOrderId);

                return new ResponseEntity<>(HttpStatus.OK);
            } else
                return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        } catch (IntegrationException e) {
            // Error handling for failed deserialization
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isValidRequest(String requestBody, String signature) {
        try {
            return Utils.verifyWebhookSignature(requestBody, signature, "123456789");
        } catch (RazorpayException e) {
            e.printStackTrace();
            return false;
        }
    }

}
