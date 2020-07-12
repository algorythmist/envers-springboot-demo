package com.tecacet.demo.envers.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Customer extends AbstractPersistable<Long> {

    @NotEmpty
    @EqualsAndHashCode.Include
    private String username;

    private String email;
}
