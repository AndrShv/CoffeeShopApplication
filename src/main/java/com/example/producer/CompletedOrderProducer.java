package com.example.producer;

import com.example.config.rabbitMq.RabbitConfig;
import com.example.entity.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompletedOrderProducer {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public void sendCompletedOrder(Order order) {
        try {
            String json = objectMapper.writeValueAsString(order);
            rabbitTemplate.convertAndSend(
                    RabbitConfig.EXCHANGE,
                    RabbitConfig.COMPLETED_ROUTING_KEY,
                    json
            );
            System.out.println("🎉 Отправлен готовый заказ: " + json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
