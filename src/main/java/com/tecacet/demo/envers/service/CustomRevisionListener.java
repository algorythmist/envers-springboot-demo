package com.tecacet.demo.envers.service;

import com.tecacet.demo.envers.entity.audit.CustomRevision;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.context.SecurityContextHolder;

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
        revision.setUsername(getCurrentUser());
    }

    private String getCurrentUser() {
        if (SecurityContextHolder.getContext() == null
                || SecurityContextHolder.getContext().getAuthentication() == null) {
            return "ADMIN";
        }
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
