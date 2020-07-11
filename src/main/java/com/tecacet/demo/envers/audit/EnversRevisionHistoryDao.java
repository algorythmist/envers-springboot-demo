package com.tecacet.demo.envers.audit;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Table;
import javax.transaction.Transactional;


@Component
@Transactional
public class EnversRevisionHistoryDao implements RevisionHistoryDao {

    private final EntityManager entityManager;

    @Autowired
    public EnversRevisionHistoryDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public AuditReader getAuditReader() {
        return AuditReaderFactory.get(entityManager);
    }

    @Override
    public <T> List<T> getAllRevisionsForEntity(Class<T> clazz, long id) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        AuditQuery auditQuery = auditReader.createQuery().forRevisionsOfEntity(clazz, true, true);
        auditQuery.add(AuditEntity.property("id").eq(id));
        return auditQuery.getResultList();
    }

    @Override
    public <T> void deleteAllRevisions(Class<T> type) {
        Table tableAnnotation = type.getAnnotation(Table.class);
        if (tableAnnotation == null) {
            throw new IllegalArgumentException(); // TODO friendly
        }
        String tableName = tableAnnotation.name();
        String auditTableName = tableName + "_AUD"; //TODO: get from properties
        deleteAllRevisions(auditTableName);

    }

    private void deleteAllRevisions(String auditTableName) {
        String deleteSql = String.format("delete from %s", auditTableName);
        entityManager.createNativeQuery(deleteSql).executeUpdate();
    }
}
