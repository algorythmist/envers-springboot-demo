# Auditing with Hibernate Envers

- How to customize revision tables
- How to query revision history
- write non-transactional test

- why not to extend DefaultRevisionEntity, AbstractPersistable
- when to use NotAudited

### History Tables
- All columns must be nullable
- Do not declare primary keys
- Add revision columns

## References

https://docs.jboss.org/envers/docs/
