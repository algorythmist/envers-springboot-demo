# Auditing with Hibernate Envers

### Setup

```java
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class PersistenceConfig {

}
```

This configuration allows integrating spring-data-envers project to your Spring Boot project.

- @Audited


### Defining History Tables
- All columns must be nullable
- Do not declare primary keys
- Add revision columns


### How to customize revision tables

```properties
spring.jpa.properties.org.hibernate.envers.audit_table_suffix = _history
spring.jpa.properties.org.hibernate.envers.revision_field_name = revision_id
spring.jpa.properties.org.hibernate.envers.revision_type_field_name = revision_type
```


### How to query revision history

- Horizontal/vertical

https://thorben-janssen.com/hibernate-envers-query-data-audit-log/

- Extend RevisionRepository

See CustomOrderRepositoryTest

### Testing

- write non-transactional tests. Transactional tests will not have persisted revisions. For example, try adding @Transactional to CustomerOrderRepositoryTest
- write a method to cleanup the revision tables after each test

### Write custom revision entity

- customize the revision number and timestamp
- add additional columns, such as identifier of the user 
- avoid extending DefaultRevisionEntity (why?)
- avoid extending AbstractPersistable (why?)
- To populate fields add a custom RevisionListener
- In recent versions of Spring, you can inject beans into the rev listener. In older versions, you had to implement ApplicationContextAware


### why not to extend DefaultRevisionEntity, AbstractPersistable

### @Audited and  @NotAudited

- Use @NotAudited for fields that are not in the audit tables
- @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED) 
has only one usage: When you have audited entity owning the relationship to not audited entity and you want info in audit data about the id of not audited entity.

### Conditional auditing

https://thorben-janssen.com/conditional-auditing-hibernate-envers/

### ValidityAuditStrategy

- https://vladmihalcea.com/the-best-way-to-implement-an-audit-log-using-hibernate-envers/

## References

- https://docs.jboss.org/envers/docs/

- https://thorben-janssen.com/hibernate-envers-query-data-audit-log/
- 
- https://thorben-janssen.com/conditional-auditing-hibernate-envers/

- https://denuwanhimangahettiarachchi.medium.com/maintain-the-data-versioning-info-with-spring-data-envers-42b6dfc19e27

- https://vladmihalcea.com/the-best-way-to-implement-an-audit-log-using-hibernate-envers/