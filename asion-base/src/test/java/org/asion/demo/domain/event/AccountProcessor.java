package org.asion.demo.domain.event;

import org.asion.base.ddd.domain.annotations.EventListener;

/**
 * @author Asion.
 * @since 2017/3/20.
 */
public interface AccountProcessor {
    @EventListener
    void handleCreated(AccountEvent event);
}
