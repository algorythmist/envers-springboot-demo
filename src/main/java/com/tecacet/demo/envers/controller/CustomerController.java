package com.tecacet.demo.envers.controller;

import com.tecacet.demo.envers.dto.CustomerDto;
import com.tecacet.demo.envers.entity.Customer;
import com.tecacet.demo.envers.repository.CustomerRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/customers",
        consumes = "application/json",
        produces = "application/json")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;

    @Operation(summary = "Insert customer")
    @PostMapping
    public ResponseEntity<CustomerDto> create(
            @RequestBody CustomerDto customer) {
        customerRepository.save(Customer.builder()
                .username(customer.getUsername())
                .email(customer.getEmail())
                .build());
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> delete(String username) {
        var customer = customerRepository.findByUsername(username);
        if (customer.isEmpty()) {
            return ResponseEntity.ok(false);
        }
        customerRepository.delete(customer.get());
        return ResponseEntity.ok(true);
    }
}
