package com.tecacet.demo.envers.audit;

import static org.junit.jupiter.api.Assertions.*;

import com.tecacet.demo.envers.entity.Customer;
import com.tecacet.demo.envers.entity.CustomerOrder;
import com.tecacet.demo.envers.repository.CustomerOrderRepository;
import com.tecacet.demo.envers.repository.CustomerRepository;

import com.tecacet.demo.envers.repository.audit.EnversRevisionHistoryDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class EnversRevisionHistoryDaoTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerOrderRepository orderRepository;

    @Autowired
    private EnversRevisionHistoryDao revisionHistoryDao;

    @AfterEach
    public void cleanupRevisionHistory() {
        orderRepository.deleteAll();
        revisionHistoryDao.deleteAllRevisions("order_item");
        revisionHistoryDao.deleteAllRevisions("customer_order");
    }

    @Test
    void findRevisionsWhereStatusChanged() {
        Customer customer = new Customer();
        customer.setUsername("username");
        customerRepository.save(customer);

        CustomerOrder order1 = createOrder(customer, "order1", CustomerOrder.Status.IN_PROGRESS);
        CustomerOrder order2 = createOrder(customer, "order2", CustomerOrder.Status.PLACED);
        //CustomerOrder order3 = createOrder(customer, "order3", CustomerOrder.Status.SHIPPED);

        List<CustomerOrder> orders =
                revisionHistoryDao.findRevisionsWhereStatusChanged(order1.getId(), CustomerOrder.Status.SHIPPED);
        assertTrue(orders.isEmpty());

        CustomerOrder found2 = orderRepository.findById(order2.getId()).get();
        found2.setStatus(CustomerOrder.Status.SHIPPED);
        orderRepository.save(found2);

        orders =
                revisionHistoryDao.findRevisionsWhereStatusChanged(order2.getId(), CustomerOrder.Status.SHIPPED);
        assertEquals(1, orders.size());

    }

    private CustomerOrder createOrder(Customer customer, String description,
           CustomerOrder.Status status) {
        CustomerOrder order = new CustomerOrder(customer, description, status);
        order.addItem(description+"item", BigDecimal.TEN, 1);
        return orderRepository.save(order);
    }

}
