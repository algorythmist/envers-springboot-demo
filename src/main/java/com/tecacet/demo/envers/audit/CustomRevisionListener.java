package com.tecacet.demo.envers.audit;

import org.hibernate.envers.RevisionListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * When an audit revision is posted, this listener intervenes to populate
 * special fields such as the user
 *
 * @author algorythmist
 */
public class CustomRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object object) {
        CustomRevision revision = (CustomRevision) object;
				//TODO: get from security context
        revision.setUsername("authorized");
        revision.setRevisionTime(LocalDateTime.now());
    }

}
