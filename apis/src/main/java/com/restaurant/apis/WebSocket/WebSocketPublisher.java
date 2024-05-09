package com.restaurant.apis.WebSocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketPublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendOrderPlacedMessage() {
        System.out.print("calling order placed");
        messagingTemplate.convertAndSend("/topic/order-placed", "A new order has been placed!");
    }

    public void sendOrderUpdatedMessage() {
        messagingTemplate.convertAndSend("/topic/order-updated", "An order has been updated!");
    }
    public void sendPaymentMade(){
        messagingTemplate.convertAndSend("/topic/payment-made", "A payment has been made and a table got unreserved!");
    }
    public void sendKitchenStatusUpdated(){
        messagingTemplate.convertAndSend("topic/kitchenStatus-updated", "Someone started preparing order");
    }
}
