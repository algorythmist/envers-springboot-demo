package com.tecacet.demo.envers.controller;

import com.tecacet.demo.envers.repository.audit.RevisionHistoryDao;
import com.tecacet.demo.envers.dto.OrderDto;
import com.tecacet.demo.envers.entity.CustomerOrder;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping(value = "/revisions",
        consumes = "application/json",
        produces = "application/json")
@RequiredArgsConstructor
public class RevisionController {

    private final RevisionHistoryDao revisionHistoryDao;

    @Operation(summary = "Query revisions")
    @GetMapping
    public ResponseEntity<List<OrderDto>> getRevisions(
            @RequestParam long orderId) {
        var revisions = revisionHistoryDao.findAllRevisions(orderId);
        var orders = revisions.stream().map(this::orderDto).toList();
        return ResponseEntity.ok(orders);
    }

    private OrderDto orderDto(CustomerOrder order) {
        return OrderDto.builder()
                .description(order.getDescription())
                .customerUsername(order.getCustomer().getUsername())
                //TODO.items()
                .build();
    }
}
