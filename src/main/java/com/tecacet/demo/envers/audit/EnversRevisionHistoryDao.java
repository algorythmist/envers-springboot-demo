package com.tecacet.demo.envers.audit;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;


@Component
@Transactional
public class EnversRevisionHistoryDao implements RevisionHistoryDao {


    @Value("${spring.jpa.properties.org.hibernate.envers.audit_table_suffix:_aud}")
    private String auditSuffix;

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
    public void deleteAllRevisions(String tableName) {
        String auditTableName = tableName + auditSuffix;
        String deleteSql = String.format("delete from %s", auditTableName);
        entityManager.createNativeQuery(deleteSql).executeUpdate();

    }

}
