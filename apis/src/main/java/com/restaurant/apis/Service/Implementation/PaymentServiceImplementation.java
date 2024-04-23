package com.restaurant.apis.Service.Implementation;

import com.restaurant.apis.Model.PaymentRequest;
import com.restaurant.apis.Service.PaymentService;

import jakarta.transaction.Transactional;

import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
@Transactional
public class PaymentServiceImplementation implements PaymentService {

    @Override
    public String createPaymentLink(PaymentRequest paymentRequest) throws RazorpayException {
        final Logger logger = LoggerFactory.getLogger(PaymentServiceImplementation.class);


        RazorpayClient razorpay= null;
        try {
            razorpay = new RazorpayClient("rzp_test_y0N9K4UuqYFAor", "x4aoRpRLzjlW2YC290DNjbxZ");
        } catch (RazorpayException e) {
            e.printStackTrace();
        }
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount", paymentRequest.getAmount()*100);
        paymentLinkRequest.put("currency", "INR");
        paymentLinkRequest.put("accept_partial", false);
        paymentLinkRequest.put("first_min_partial_amount", paymentRequest.getAmount()*100);
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

}
