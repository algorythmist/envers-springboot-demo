package com.tecacet.demo.envers.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.tecacet.demo.envers.audit.CustomRevision;
import com.tecacet.demo.envers.audit.RevisionHistoryDao;
import com.tecacet.demo.envers.entity.Customer;
import com.tecacet.demo.envers.entity.CustomerOrder;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.data.history.Revisions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class CustomerOrderRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private RevisionHistoryDao revisionHistoryDao;

    @AfterEach
    public void cleanupRevisionHistory() {
        revisionHistoryDao.deleteAllRevisions("order_item");
        revisionHistoryDao.deleteAllRevisions("customer_order");
    }

    @Test
    public void testCrudRevisions() {
        Customer customer = new Customer();
        customer.setUsername("customer");
        customerRepository.save(customer);

        CustomerOrder order = new CustomerOrder(customer, "order1", CustomerOrder.Status.IN_PROGRESS);
        order.addItem("item1", BigDecimal.TEN, 2);

        customerOrderRepository.save(order);
        assertTrue(order.getId() > 0);

        List<CustomerOrder> orders = customerOrderRepository.findAll();
        assertEquals(1, orders.size());
        CustomerOrder found = orders.get(0);
        found.setStatus(CustomerOrder.Status.PLACED);
        customerOrderRepository.save(found);

        Revision<Integer, CustomerOrder> lastRevision =
                customerOrderRepository.findLastChangeRevision(found.getId())
                        .orElseThrow(() -> new IllegalStateException("No revisions found"));
        RevisionMetadata<Integer> revisionMetadata = lastRevision.getMetadata();
        assertEquals(RevisionMetadata.RevisionType.UPDATE, revisionMetadata.getRevisionType());
        CustomRevision customRevision = revisionMetadata.getDelegate();
        assertEquals("ADMIN", customRevision.getUsername());
        LocalDate date = customRevision.getRevisionDateTime().toLocalDate();
        assertEquals(LocalDate.now(), date);

        Revisions<Integer, CustomerOrder> revisions = customerOrderRepository.findRevisions(found.getId());
        List<Revision<Integer, CustomerOrder>> revisionList = revisions.getContent();
        assertEquals(2, revisionList.size());

        customerOrderRepository.delete(found);

        revisions = customerOrderRepository.findRevisions(found.getId());
        assertEquals(3, revisions.getContent().size());
    }


}
