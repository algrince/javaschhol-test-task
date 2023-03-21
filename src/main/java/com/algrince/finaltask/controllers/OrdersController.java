package com.algrince.finaltask.controllers;

import com.algrince.finaltask.dto.OrderDTO;
import com.algrince.finaltask.dto.UpdateOrderDTO;
import com.algrince.finaltask.models.Order;
import com.algrince.finaltask.models.User;
import com.algrince.finaltask.services.OrdersService;
import com.algrince.finaltask.services.UsersService;
import com.algrince.finaltask.utils.DTOMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class OrdersController {

    private final OrdersService ordersService;
    private final UsersService usersService;
    private final DTOMapper dtoMapper;

    @GetMapping
    public List<OrderDTO> getOrders(
            @RequestParam(required = false) Long user) {
        List<Order> orders = ordersService.selectOrders(user);
        return dtoMapper.mapList(orders, OrderDTO.class);
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderDTO> getOrder(
            @PathVariable("id") Long id) {
        Order foundOrder = ordersService.findById(id);
        OrderDTO foundOrderDTO = dtoMapper.mapClass(foundOrder, OrderDTO.class);
        return ResponseEntity.ok().body(foundOrderDTO);
    }

    @PostMapping
    public ResponseEntity<Object> addOrder(
            @Valid @RequestBody OrderDTO orderDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return new ResponseEntity<>(errors, HttpStatus.OK);
        }

        Order order = dtoMapper.mapClass(orderDTO, Order.class);
//        Manual setting of user: until login implementation
        User user = usersService.findOne(25L);
        order.setUser(user);
        ordersService.saveAndApplyChanges(order);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping("{id}")
    public ResponseEntity<Object> updateOrder (
            @PathVariable(value = "id") Long orderId,
            @Valid @RequestBody UpdateOrderDTO updateOrderDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return new ResponseEntity<>(errors, HttpStatus.OK);
        }

        Order foundOrder = ordersService.findById(orderId);
        dtoMapper.mapProperties(updateOrderDTO, foundOrder);
        ordersService.save(foundOrder);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
