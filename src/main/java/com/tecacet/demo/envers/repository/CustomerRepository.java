package com.tecacet.demo.envers.repository;

import com.tecacet.demo.envers.domain.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
