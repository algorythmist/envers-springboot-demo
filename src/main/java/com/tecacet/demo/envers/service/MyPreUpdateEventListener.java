package com.tecacet.demo.envers.service;

import com.tecacet.demo.envers.entity.Customer;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.event.spi.EnversPreUpdateEventListenerImpl;
import org.hibernate.event.spi.PreUpdateEvent;

@Slf4j
public class MyPreUpdateEventListener extends EnversPreUpdateEventListenerImpl  {
    public MyPreUpdateEventListener(EnversService enversService) {
        super(enversService);
    }

    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {
        if (event.getEntity() instanceof Customer) {
            log.debug("I caught a customer.");
            return false;
        }
        return super.onPreUpdate(event);
    }
}
