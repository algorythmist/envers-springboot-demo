package com.tecacet.demo.envers.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Customer extends AbstractPersistable<Long> {

    @NotNull
    @EqualsAndHashCode.Include
    private String username;

    private String email;
}
