package com.algrince.finaltask.dto;

import com.algrince.finaltask.enums.DeliveryMethod;
import com.algrince.finaltask.enums.OrderStatus;
import com.algrince.finaltask.enums.PaymentMethod;
import com.algrince.finaltask.enums.PaymentStatus;
import com.algrince.finaltask.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    private java.util.Calendar createdDate;

    private LoggedUserDTO user;

    private List<OrderProductDTO> orderProducts;
}
