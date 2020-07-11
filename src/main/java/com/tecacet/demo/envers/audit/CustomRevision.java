package com.tecacet.demo.envers.audit;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import javax.persistence.Entity;

/**
 * Custom mapping for Hibernate Envers revision table
 *
 * @author algorythmist
 */
@Getter
@Setter
@Entity
@RevisionEntity(CustomRevisionListener.class)
public class CustomRevision extends DefaultRevisionEntity {

    private String username;

    private LocalDateTime revisionTime;

}
