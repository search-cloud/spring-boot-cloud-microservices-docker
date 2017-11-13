package org.asion.demo.domain.infrastructure.domain.event;

import org.asion.base.ddd.domain.annotations.EventListener;
import org.asion.demo.domain.event.AccountEvent;
import org.asion.demo.domain.event.AccountProcessor;

/**
 * account processor implementation
 *
 * @author Asion
 */
public class AccountEventProcessor implements AccountProcessor {

    @EventListener(AccountEvent.class)
    public void handleCreated(AccountEvent accountEvent) {

    }
}
