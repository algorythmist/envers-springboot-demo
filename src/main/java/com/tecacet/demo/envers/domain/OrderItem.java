package com.tecacet.demo.envers.domain;

import org.hibernate.envers.Audited;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@Audited
@NoArgsConstructor
public class OrderItem  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long id;

    @NotNull
    @ManyToOne
    private CustomerOrder order;

    @NotEmpty
    private String itemIdentifier;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;

    @NotNull
    @Min(1)
    @Column(name = "number")
    private int count;

    public OrderItem(@NotNull CustomerOrder order, @NotEmpty String itemIdentifier,
            @NotNull @Min(0) BigDecimal price, @NotNull @Min(1) int count) {
        this.order = order;
        this.itemIdentifier = itemIdentifier;
        this.price = price;
        this.count = count;
    }

    public BigDecimal getAmount() {
        return price.multiply(BigDecimal.valueOf(count));
    }
}
