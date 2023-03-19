package com.algrince.finaltask.dto;

import com.algrince.finaltask.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateOrderDTO {

    private Long id;
    private OrderStatus orderStatus;
}
