package com.example.producer;

import com.example.config.rabbitMq.RabbitConfig;
import com.example.entity.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {
    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    public void sendOrder(Order order) {
        try {
            String json = objectMapper.writeValueAsString(order);
            rabbitTemplate.convertAndSend(
                    RabbitConfig.EXCHANGE,
                    RabbitConfig.ROUTING_KEY,
                    json
            );
            System.out.println("Отправлен заказ: " + json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
