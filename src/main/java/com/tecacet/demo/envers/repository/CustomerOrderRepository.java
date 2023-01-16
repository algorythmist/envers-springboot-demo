package com.tecacet.demo.envers.repository;

import com.tecacet.demo.envers.entity.CustomerOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long>, RevisionRepository<CustomerOrder, Long, Integer> {
}
