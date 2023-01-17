package com.tecacet.demo.envers.audit;

import com.tecacet.demo.envers.entity.CustomerOrder;

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
    public List<CustomerOrder> findAllRevisions(long orderId) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        AuditQuery auditQuery = auditReader.createQuery()
                .forRevisionsOfEntity(CustomerOrder.class, true, false)
                .add(AuditEntity.id().eq(orderId));
        return auditQuery.getResultList();
    }

    @Override
    public List<CustomerOrder> findRevisionsWhereStatusChanged(long orderId, CustomerOrder.Status status) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        AuditQuery auditQuery = auditReader.createQuery()
                .forRevisionsOfEntity(CustomerOrder.class, true, false)
                .add(AuditEntity.id().eq(orderId))
                .add(AuditEntity.property("status").eq(status));

        return auditQuery.getResultList();
    }

    @Override
    public void deleteAllRevisions(String tableName) {
        String auditTableName = tableName + auditSuffix;
        String deleteSql = String.format("delete from %s", auditTableName);
        entityManager.createNativeQuery(deleteSql).executeUpdate();

    }

}
