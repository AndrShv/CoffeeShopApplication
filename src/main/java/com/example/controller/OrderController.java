package com.example.controller;

import com.example.dto.OrderDTO;
import com.example.entity.Order;
import com.example.producer.OrderProducer;
import com.example.producer.CompletedOrderProducer;
import com.example.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderProducer producer;
    private final CompletedOrderProducer completedOrderProducer;
    private final OrderRepository orderRepository;

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setOrderId(orderDTO.getOrderId());
        order.setCustomerName(orderDTO.getCustomerName());
        order.setDrinkName(orderDTO.getDrinkName());
        orderRepository.save(order);
        producer.sendOrder(order);
        return ResponseEntity.ok("Заказ принят: " + order.getDrinkName());
    }

    @PostMapping("/complete")
    public ResponseEntity<String> completeOrder(@RequestBody OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setOrderId(orderDTO.getOrderId());
        order.setCustomerName(orderDTO.getCustomerName());
        order.setDrinkName(orderDTO.getDrinkName());
        orderRepository.save(order);
        completedOrderProducer.sendCompletedOrder(order);
        return ResponseEntity.ok("Заказ готов: " + order.getDrinkName());
    }
}
