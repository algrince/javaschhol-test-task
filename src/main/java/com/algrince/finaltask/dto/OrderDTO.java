package com.algrince.finaltask.dto;

import com.algrince.finaltask.enums.DeliveryMethod;
import com.algrince.finaltask.enums.OrderStatus;
import com.algrince.finaltask.enums.PaymentMethod;
import com.algrince.finaltask.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDTO {

    private Long id;

    private PaymentMethod paymentMethod;

    private DeliveryMethod deliveryMethod;

    private PaymentStatus paymentStatus;

    private OrderStatus orderStatus;

    private Double orderSum;

    private AddressDTO address;
}
