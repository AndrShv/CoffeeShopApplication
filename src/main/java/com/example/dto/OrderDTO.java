package com.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private String orderId;
    private String customerName;
    private String drinkName;
}
