package com.tecacet.demo.envers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(name = "Order", description = "An order")
public class OrderDto {

    @Schema(example = "username")
    private String customerUsername;
    @Schema(example = "description")
    private String description;
    @Schema(description = "Order items")
    private List<OrderItemDto> items;
}
