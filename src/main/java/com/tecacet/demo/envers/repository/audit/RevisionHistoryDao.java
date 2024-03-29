package com.tecacet.demo.envers.repository.audit;

import com.tecacet.demo.envers.entity.CustomerOrder;

import java.util.List;

/**
 * Utility Data Access Object for obtaining revisions of any type of object
 *
 * @author algorythmist
 */
public interface RevisionHistoryDao {

    List<CustomerOrder> findAllRevisions(long orderId);

    List<CustomerOrder> findRevisionsWhereStatusChanged(long id, CustomerOrder.Status status);

    /**
     * Delete the entire revision history for an object. This method is strictly
     * intended for tests. There should be no business scenario where this is
     * allowed
     *
     * @param tableName the table name of the audited entity
     */
    void deleteAllRevisions(String tableName);

}
