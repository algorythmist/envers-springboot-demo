package com.tecacet.demo.envers.repository;

import com.tecacet.demo.envers.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
