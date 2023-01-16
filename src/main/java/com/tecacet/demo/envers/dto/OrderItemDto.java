package com.tecacet.demo.envers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Builder
public class OrderItemDto {

    @Schema(example = "bread")
    private String itemIdentifier;

    @NotNull
    @DecimalMin("0.0")
    @Schema(example = "5.0")
    private BigDecimal price;

    @NotNull
    @Min(1)
    @Schema(example = "2")
    private int count;
}
