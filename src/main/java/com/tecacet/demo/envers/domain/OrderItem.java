package com.tecacet.demo.envers.domain;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends AbstractPersistable<Long> {

    @NotNull
    @ManyToOne
    private CustomerOrder order;

    @NotEmpty
    private String itemIdentifier;

    @NotNull
    @Min(0)
    private BigDecimal price;

    @NotNull
    @Min(1)
    private int count;

    public BigDecimal getAmount() {
        return price.multiply(BigDecimal.valueOf(count));
    }
}
