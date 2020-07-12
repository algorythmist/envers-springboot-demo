package com.tecacet.demo.envers.domain;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
@Audited
public class CustomerOrder extends AbstractPersistable<Long> {

    public enum Status {
        IN_PROGRESS, PLACED, SHIPPED, DELIVERED
    }

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne
    @NotNull
    private Customer customer;

    @NotNull
    private String description;

    @Min(0)
    private BigDecimal totalAmount;

    @NotNull
    @Setter
    private Status status;

    @NotEmpty
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> items;

    public CustomerOrder(@NotNull Customer customer, @NotNull String description,
         @NotNull Status status) {
        this.customer = customer;
        this.description = description;
        this.status = status;
    }

    public void addItem(String identifier, BigDecimal price, int number) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(new OrderItem(this, identifier, price, number));
    }
}
