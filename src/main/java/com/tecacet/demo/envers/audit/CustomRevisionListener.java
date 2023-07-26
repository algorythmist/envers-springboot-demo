package com.tecacet.demo.envers.audit;

import com.tecacet.demo.envers.service.UserService;
import org.hibernate.envers.RevisionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * When an audit revision is posted, this listener intervenes to populate
 * special fields such as the user
 *
 * @author algorythmist
 */
@Component
public class CustomRevisionListener implements RevisionListener {

    @Autowired
    private UserService userService;

    @Override
    public void newRevision(Object object) {
        CustomRevision revision = (CustomRevision) object;
        revision.setUsername(userService.getCurrentUser());
    }

}
