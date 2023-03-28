package com.algrince.finaltask.controllers;

import com.algrince.finaltask.dto.OrderDTO;
import com.algrince.finaltask.dto.OrderProductDTO;
import com.algrince.finaltask.dto.UpdateOrderDTO;
import com.algrince.finaltask.exceptions.InvalidFormException;
import com.algrince.finaltask.models.Order;
import com.algrince.finaltask.models.OrderProduct;
import com.algrince.finaltask.models.Product;
import com.algrince.finaltask.models.User;
import com.algrince.finaltask.services.OrdersService;
import com.algrince.finaltask.services.ProductsService;
import com.algrince.finaltask.services.UsersService;
import com.algrince.finaltask.utils.DTOMapper;
import com.algrince.finaltask.validators.OrderValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;
    private final UsersService usersService;
    private final ProductsService productsService;
    private final OrderValidator orderValidator;
    private final DTOMapper dtoMapper;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<OrderDTO> getUserOrdersList(
            @RequestParam(required = true) Long user,
            Principal principal) {
        User associatedUser = usersService.findById(user);
        usersService.checkAccess(principal, associatedUser);

        List<Order> orders = ordersService.findByUser(associatedUser);
        return dtoMapper.mapList(orders, OrderDTO.class);
    }

    @GetMapping("all")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public List<OrderDTO> getOrdersList() {
        List<Order> orders = ordersService.findAll();
        return dtoMapper.mapList(orders, OrderDTO.class);
    }

    @GetMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrderDTO> getOrder(
            @PathVariable("id") Long id,
            Principal principal) {
        Order foundOrder = ordersService.findById(id);
        User associatedUser = foundOrder.getUser();
        usersService.checkAccess(principal, associatedUser);

        OrderDTO foundOrderDTO = dtoMapper.mapClass(foundOrder, OrderDTO.class);
        return ResponseEntity.ok().body(foundOrderDTO);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> addOrder(
            @Valid @RequestBody OrderDTO orderDTO,
            Principal principal,
            BindingResult bindingResult) {

        User associatedUser = usersService.findById(orderDTO.getUser().getId());
        usersService.checkAccess(principal, associatedUser);

        Order order = dtoMapper.mapClass(orderDTO, Order.class);

        List<OrderProduct> orderedProducts = new ArrayList<>();

        for (OrderProductDTO productInfo: orderDTO.getOrderProducts()) {
            OrderProduct newProductInOrder = new OrderProduct();
            Long productId = productInfo.getAddedProductId();
            Product foundProduct = productsService.findById(productId);
            newProductInOrder.setProduct(foundProduct);
            newProductInOrder.setQuantity(productInfo.getQuantity());
            newProductInOrder.setOrder(order);
            orderedProducts.add(newProductInOrder);
        }

        order.setOrderProducts(orderedProducts);
        order.setUser(associatedUser);

        orderValidator.validate(order, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new InvalidFormException(bindingResult);
        }

        ordersService.saveAndApplyChanges(order);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> updateOrder(
            @PathVariable(value = "id") Long orderId,
            @Valid @RequestBody UpdateOrderDTO updateOrderDTO,
            Principal principal,
            BindingResult bindingResult) {

        Order foundOrder = ordersService.findById(orderId);
        User associatedUser = foundOrder.getUser();
        usersService.checkAccess(principal, associatedUser);

        if (bindingResult.hasErrors()) {
            throw new InvalidFormException(bindingResult);
        }

        dtoMapper.mapProperties(updateOrderDTO, foundOrder);
        ordersService.save(foundOrder);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<String> deleteOrder(
            @PathVariable(value = "id") Long orderId) {
        Order orderToDelete = ordersService.findById(orderId);
        ordersService.softDelete(orderToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
