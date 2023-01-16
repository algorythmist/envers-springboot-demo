package com.tecacet.demo.envers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class CustomerDto {

    @NotNull
    @Schema(example = "username")
    private String username;
    @NotNull
    @Schema(example = "leo.miller@nomail.com")
    private String email;
}
