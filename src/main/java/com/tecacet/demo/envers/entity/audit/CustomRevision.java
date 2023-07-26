package com.tecacet.demo.envers.entity.audit;

import com.tecacet.demo.envers.service.CustomRevisionListener;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Custom mapping for Hibernate Envers revision table
 *
 * @author algorythmist
 */
@Getter
@Setter
@Entity
@Table(name = "revision_info")
@RevisionEntity(CustomRevisionListener.class)
public class CustomRevision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    private int id;

    @RevisionTimestamp
    @Column(name = "revision_timestamp")
    private Date timestamp;

    private String username;

    public LocalDateTime getRevisionDateTime() {
        return timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
