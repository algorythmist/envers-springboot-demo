package com.tecacet.demo.envers.controller;

import com.tecacet.demo.envers.dto.OrderDto;
import com.tecacet.demo.envers.service.OrderService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/orders",
        consumes = "application/json",
        produces = "application/json")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Create order")
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(
            @RequestBody OrderDto order) {
        orderService.createOrder(order);
        return ResponseEntity.ok(order);
    }

}
