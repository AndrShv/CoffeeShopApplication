package com.example.consumer;

import com.example.config.rabbitMq.RabbitConfig;
import com.example.entity.Order;
import com.example.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {
    private final OrderRepository orderRepository;

    public OrderConsumer(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void receiveOrder(String orderJson) {
        System.out.println("☕ Получен заказ: " + orderJson);
        try {
            Order order = new ObjectMapper().readValue(orderJson, Order.class);
            orderRepository.save(order);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("✅ Заказ выполнен: " + orderJson);
    }
}