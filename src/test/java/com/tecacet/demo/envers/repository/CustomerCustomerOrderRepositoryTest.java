package com.tecacet.demo.envers.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.tecacet.demo.envers.audit.CustomRevision;
import com.tecacet.demo.envers.audit.RevisionHistoryDao;
import com.tecacet.demo.envers.domain.CustomerOrder;

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
class CustomerCustomerOrderRepositoryTest {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private RevisionHistoryDao revisionHistoryDao;

    @Test
    public void testCrudRevisions() {
        CustomerOrder order = new CustomerOrder("test", BigDecimal.TEN, CustomerOrder.Status.IN_PROGRESS);
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
        assertEquals("authorized", customRevision.getUsername());
        assertEquals(LocalDate.now(), customRevision.getRevisionTime().toLocalDate());

        Revisions<Integer, CustomerOrder> revisions = customerOrderRepository.findRevisions(found.getId());
        List<Revision<Integer, CustomerOrder>> revisionList = revisions.getContent();
        assertEquals(2, revisionList.size());

        customerOrderRepository.delete(found);

        revisions = customerOrderRepository.findRevisions(found.getId());
        assertEquals(3, revisions.getContent().size());
    }



}