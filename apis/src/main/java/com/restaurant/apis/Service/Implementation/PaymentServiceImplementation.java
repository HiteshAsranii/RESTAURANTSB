package com.restaurant.apis.Service.Implementation;

import com.restaurant.apis.Model.Orders;
import com.restaurant.apis.Model.PaymentRequest;
import com.restaurant.apis.Model.Payments;
import com.restaurant.apis.Model.WebhookPayload;
import com.restaurant.apis.Service.OrderService;
import com.restaurant.apis.Service.PaymentService;
import com.restaurant.apis.Service.RestaurantTableService;
import com.restaurant.apis.WebSocket.WebSocketPublisher;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.hibernate.boot.beanvalidation.IntegrationException;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.Order;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

@Service
@Transactional
public class PaymentServiceImplementation implements PaymentService {

    private final RestaurantTableService restaurantTableService;

    @Autowired
    private EntityManager entityManager;

    private OrderService orderService;

    private WebSocketPublisher webSocketPublisher;

    public PaymentServiceImplementation(RestaurantTableService restaurantTableService, OrderService orderService) {
        super();
        this.orderService = orderService;
        this.restaurantTableService = restaurantTableService;
    }

    @Override
    public String createPaymentLink(PaymentRequest paymentRequest) throws RazorpayException {
        final Logger logger = LoggerFactory.getLogger(PaymentServiceImplementation.class);

        RazorpayClient razorpay = null;
        try {
            razorpay = new RazorpayClient("rzp_test_y0N9K4UuqYFAor", "x4aoRpRLzjlW2YC290DNjbxZ");
        } catch (RazorpayException e) {
            e.printStackTrace();
        }
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount", paymentRequest.getAmount() * 100);
        paymentLinkRequest.put("currency", "INR");
        paymentLinkRequest.put("accept_partial", false);
        paymentLinkRequest.put("first_min_partial_amount", paymentRequest.getAmount() * 100);
        paymentLinkRequest.put("expire_by", paymentRequest.getExpireBy());
        paymentLinkRequest.put("reference_id", paymentRequest.getReferenceId());
        paymentLinkRequest.put("description", "Payment for Order Id" + paymentRequest.getReferenceId());
        JSONObject customer = new JSONObject();
        customer.put("name", paymentRequest.getCustomerName());
        customer.put("contact", paymentRequest.getCustomerContactNumber());
        customer.put("email", paymentRequest.getCustomerEmail());
        paymentLinkRequest.put("customer", customer);
        JSONObject notify = new JSONObject();
        notify.put("sms", paymentRequest.isNotifySms());
        notify.put("email", paymentRequest.isNotifyEmail());
        paymentLinkRequest.put("notify", notify);
        paymentLinkRequest.put("reminder_enable", true);
        JSONObject notes = new JSONObject();
        notes.put("OrderId", paymentRequest.getReferenceId());
        paymentLinkRequest.put("notes", notes);
        paymentLinkRequest.put("callback_url", "https://example-callback-url.com/");
        paymentLinkRequest.put("callback_method", "get");
        PaymentLink payment = null;
        try {
            payment = razorpay.paymentLink.create(paymentLinkRequest);
            logger.info(payment.toString());

        } catch (RazorpayException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.toString();
        }
        return payment.toString();
    }

    @Override
    public ResponseEntity<Object> verifyPayment(String requestBody, String Signature)
            throws JsonMappingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Deserializing it
            WebhookPayload webhookPayload = objectMapper.readValue(requestBody, WebhookPayload.class);
            // Print the deserialized JSON map
            System.out.println(webhookPayload);

            if (isValidRequest(requestBody, Signature)) { // Return a success response
                Payments payment = new Payments();
                payment.setPaymentId(webhookPayload.getPayload().getPayment().getEntity().getId());
                payment.setRazorPayId(webhookPayload.getPayload().getPayment().getEntity().getOrderId());
                payment.setAmount((double) (webhookPayload.getPayload().getPayment().getEntity().getAmount() / 100));
                payment.setPaymentMethod(webhookPayload.getPayload().getPayment().getEntity().getCard().getType());
                int restaurantOrderId = Integer.parseInt(
                        webhookPayload.getPayload().getPayment().getEntity().getNotes().getRestaurantOrderId());
                Orders order = entityManager.find(Orders.class, restaurantOrderId);
                payment.setOrderId(order);
                System.out.println("payment entity---->" + payment);
                System.out.println("Unreserving table");
                restaurantTableService.unReserveTable(restaurantOrderId);
                //persisting the payment details
                entityManager.persist(payment);
                orderService.changeOrderStatusToComplete(order);
                //notify all users when a payment is made and table is unreserved.
                webSocketPublisher.sendPaymentMade();

                return new ResponseEntity<>(HttpStatus.OK);
            } else
                return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        } catch (IntegrationException e) {
            // Error handling for failed deserialization
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean isValidRequest(String requestBody, String signature) {
        try {
            return Utils.verifyWebhookSignature(requestBody, signature, "123456789");
        } catch (RazorpayException e) {
            e.printStackTrace();
            return false;
        }
    }

}
