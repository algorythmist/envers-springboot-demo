package com.tecacet.demo.envers.service;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.envers.boot.internal.EnversIntegrator;
import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.event.spi.*;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.*;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.jboss.logging.Logger;

public class MyIntegrator implements Integrator {
    private static final Logger log = Logger.getLogger(EnversIntegrator.class);
    public static final String AUTO_REGISTER = "hibernate.envers.autoRegisterListeners";

   @Override
    public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        EnversService enversService = (EnversService)serviceRegistry.getService(EnversService.class);
        if (!enversService.isEnabled()) {
            log.debug("Skipping Envers listener registrations : EnversService disabled");
        } else {
//            boolean autoRegister = (Boolean)((ConfigurationService)serviceRegistry.getService(ConfigurationService.class)).getSetting("hibernate.envers.autoRegisterListeners", StandardConverters.BOOLEAN, true);
//            if (!autoRegister) {
//                log.debug("Skipping Envers listener registrations : Listener auto-registration disabled");
//            } else if (!enversService.isInitialized()) {
//                throw new HibernateException("Expecting EnversService to have been initialized prior to call to EnversIntegrator#integrate");
//            } else if (!enversService.getEntitiesConfigurations().hasAuditedEntities()) {
//                log.debug("Skipping Envers listener registrations : No audited entities found");
//            } else {
                EventListenerRegistry listenerRegistry = (EventListenerRegistry)serviceRegistry.getService(EventListenerRegistry.class);
                listenerRegistry.addDuplicationStrategy(EnversListenerDuplicationStrategy.INSTANCE);
                if (enversService.getEntitiesConfigurations().hasAuditedEntities()) {
                    listenerRegistry.appendListeners(EventType.POST_DELETE, new PostDeleteEventListener[]{new EnversPostDeleteEventListenerImpl(enversService)});
                    listenerRegistry.appendListeners(EventType.POST_INSERT, new PostInsertEventListener[]{new EnversPostInsertEventListenerImpl(enversService)});
                    listenerRegistry.appendListeners(EventType.PRE_UPDATE, new PreUpdateEventListener[]{new MyPreUpdateEventListener(enversService)});
                    listenerRegistry.appendListeners(EventType.POST_UPDATE, new PostUpdateEventListener[]{new EnversPostUpdateEventListenerImpl(enversService)});
                    listenerRegistry.appendListeners(EventType.POST_COLLECTION_RECREATE, new PostCollectionRecreateEventListener[]{new EnversPostCollectionRecreateEventListenerImpl(enversService)});
                    listenerRegistry.appendListeners(EventType.PRE_COLLECTION_REMOVE, new PreCollectionRemoveEventListener[]{new EnversPreCollectionRemoveEventListenerImpl(enversService)});
                    listenerRegistry.appendListeners(EventType.PRE_COLLECTION_UPDATE, new PreCollectionUpdateEventListener[]{new EnversPreCollectionUpdateEventListenerImpl(enversService)});
                }

            }
        //}
    }

    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
    }
}
