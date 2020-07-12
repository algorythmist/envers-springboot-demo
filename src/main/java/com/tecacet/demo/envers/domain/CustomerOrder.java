package com.tecacet.demo.envers.domain;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
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

    @NotNull
    @Min(0)
    private BigDecimal totalAmount;

    @NotNull
    private Status status;

}
