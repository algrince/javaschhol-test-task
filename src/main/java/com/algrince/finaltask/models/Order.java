package com.algrince.finaltask.models;

import com.algrince.finaltask.enums.DeliveryMethod;
import com.algrince.finaltask.enums.OrderStatus;
import com.algrince.finaltask.enums.PaymentMethod;
import com.algrince.finaltask.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "delivery_method")
    @Enumerated(EnumType.STRING)
    private DeliveryMethod deliveryMethod;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "order_sum")
    private Double orderSum;

    @Column(name = "deleted")
    private boolean isDeleted;


    // Column name is due to the current naming of MySQL database
    @ManyToOne
    @JoinColumn(name = "client_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;


    @ManyToMany
    @JoinTable(
            name = "order_has_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

}
