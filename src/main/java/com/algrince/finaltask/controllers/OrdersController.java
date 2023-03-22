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
import org.springframework.security.access.prepost.PreAuthorize;
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
//    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN') or #userId == authentication.principal.id")
    public List<OrderDTO> getOrders(
            @RequestParam(required = false) Long user) {
        List<Order> orders = ordersService.selectOrders(user);
        return dtoMapper.mapList(orders, OrderDTO.class);
    }

    @GetMapping("{id}")
//    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN') or #userId == authentication.principal.id")
    public ResponseEntity<OrderDTO> getOrder(
            @PathVariable("id") Long id) {
        Order foundOrder = ordersService.findById(id);
        OrderDTO foundOrderDTO = dtoMapper.mapClass(foundOrder, OrderDTO.class);
        return ResponseEntity.ok().body(foundOrderDTO);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
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

//      detached entity passed to persist: com.algrince.finaltask.models.User
        User associatedUser = usersService.findById(orderDTO.getUser().getId());
        order.setUser(associatedUser);
        
        ordersService.saveAndApplyChanges(order);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping("{id}")
//    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN') or #userId == authentication.principal.id")
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
