package com.tecacet.demo.envers.audit;

import org.hibernate.envers.AuditReader;

import java.util.List;

/**
 * Utility Data Access Object for obtaining revisions of any type of object
 *
 * @author algorythmist
 */
public interface RevisionHistoryDao {

    /**
     * Delete the entire revision history for an object. This method is strictly
     * intended for tests. There should be no business scenario where this is
     * allowed
     *
     * @param tableName the table name of the audited entity
     */
    void deleteAllRevisions(String tableName);

    /**
     * Get all revisions for an entity of a specific id. The oldest revision is
     * the first element in the list
     *
     * @param type the entity type
     * @param id   the entity id
     * @return all revisions
     */
    <T> List<T> getAllRevisionsForEntity(Class<T> type, long id);

    /**
     * Get an envers audit reader to create any query supported by Envers The
     * caller must use this within a transaction
     *
     * @return the audit reader
     */
    AuditReader getAuditReader();
}
