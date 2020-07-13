package com.tecacet.demo.envers.repository;

import com.tecacet.demo.envers.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void crud() {
        Customer customer = new Customer();
        customer.setUsername("username");
        customer.setEmail("user@email.com");
        customerRepository.save(customer);

        Customer found = customerRepository.findById(customer.getId())
                .orElseThrow(() -> new IllegalStateException("Where is my customer?"));
        assertEquals(LocalDate.now(), found.getCreated().toLocalDate());

        customerRepository.delete(customer);
    }

}