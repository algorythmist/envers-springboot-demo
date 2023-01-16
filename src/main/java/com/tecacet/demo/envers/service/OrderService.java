package com.tecacet.demo.envers.service;

import com.tecacet.demo.envers.dto.OrderDto;
import com.tecacet.demo.envers.entity.CustomerOrder;
import com.tecacet.demo.envers.repository.CustomerOrderRepository;
import com.tecacet.demo.envers.repository.CustomerRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerRepository customerRepository;
    private final CustomerOrderRepository customerOrderRepository;

    public void createOrder(OrderDto orderDto) {
        var customer = customerRepository.findByUsername(orderDto.getCustomerUsername())
                .orElseThrow();
        var order = new CustomerOrder(customer,
                orderDto.getDescription(),
                CustomerOrder.Status.IN_PROGRESS);
        orderDto.getItems().forEach(
                orderItem -> order.addItem(orderItem.getItemIdentifier(),
                        orderItem.getPrice(),
                        orderItem.getCount()));
        customerOrderRepository.save(order);
    }
}
