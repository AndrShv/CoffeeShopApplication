package com.example.consumer;

import com.example.config.rabbitMq.RabbitConfig;
import com.example.entity.Order;
import com.example.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class CompletedOrderConsumer {

    private final OrderRepository orderRepository;

    public CompletedOrderConsumer(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @RabbitListener(queues = RabbitConfig.COMPLETED_QUEUE)
    public void receiveCompletedOrder(String orderJson) {
        System.out.println("🎉 Готовый заказ: " + orderJson);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        try {
            Order order = new ObjectMapper().readValue(orderJson, Order.class);
            orderRepository.save(order);
            System.out.println("✅ Заказ готов для : " + order.getCustomerName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
