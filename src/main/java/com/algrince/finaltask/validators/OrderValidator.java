package com.algrince.finaltask.validators;

import com.algrince.finaltask.models.Order;
import com.algrince.finaltask.models.OrderProduct;
import com.algrince.finaltask.models.Product;
import com.algrince.finaltask.models.User;
import com.algrince.finaltask.services.OrdersService;
import com.algrince.finaltask.services.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class OrderValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Order.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Order order = (Order) target;

        for (OrderProduct orderProduct: order.getOrderProducts()) {
            Product productInOrder = orderProduct.getProduct();
            int productQuantity = orderProduct.getQuantity();
            int productInOrderStock = productInOrder.getStock();

            if (productInOrderStock < productQuantity) {
                errors.rejectValue(
                        "orderProducts",
                        "order.orderProducts.quantity.invalid",
                        "You are trying to buy too much of " + productInOrder.getTitle() +
                        ". The current stock of the product is: " + productInOrderStock);
            }
        }
    }
}
