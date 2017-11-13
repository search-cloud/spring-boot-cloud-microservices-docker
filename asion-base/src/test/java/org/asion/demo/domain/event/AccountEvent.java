package org.asion.demo.domain.event;

import org.asion.base.ddd.domain.annotations.DomainEvent;
import org.asion.base.ddd.domain.event.BaseDomainEvent;
import org.asion.demo.domain.model.Account;

/**
 * @author Asion.
 * @since 2017/3/20.
 */
@DomainEvent
public class AccountEvent extends BaseDomainEvent<Account> {
    public static String CREATED_TYPE = "created";
    public static String BLOCKED_TYPE = "blocked";


    public AccountEvent(String type, Account account) {
        setType(type);
        setPayload(account);
    }
}
