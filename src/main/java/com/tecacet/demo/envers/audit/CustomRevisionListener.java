package com.tecacet.demo.envers.audit;

import com.tecacet.demo.envers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.RevisionListener;
import org.springframework.stereotype.Component;

/**
 * When an audit revision is posted, this listener intervenes to populate
 * special fields such as the user
 *
 * @author algorythmist
 */
@Component
@RequiredArgsConstructor
public class CustomRevisionListener implements RevisionListener {

    private final UserService userService;

    @Override
    public void newRevision(Object object) {
        CustomRevision revision = (CustomRevision) object;
        revision.setUsername(userService.getCurrentUser());
    }

}
