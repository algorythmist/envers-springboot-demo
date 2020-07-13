package com.tecacet.demo.envers.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Audited
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long id;

    public enum Status {
        IN_PROGRESS, PLACED, SHIPPED, DELIVERED
    }

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne
    @NotNull
    private Customer customer;

    @NotNull
    @ToString.Include
    private String description;

    @Min(0)
    private BigDecimal totalAmount;

    @NotNull
    @ToString.Include
    @Setter
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotEmpty
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItem> items;

    @CreationTimestamp
    @NotAudited
    private LocalDateTime created;

    @UpdateTimestamp
    @NotAudited
    private LocalDateTime updated;

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
