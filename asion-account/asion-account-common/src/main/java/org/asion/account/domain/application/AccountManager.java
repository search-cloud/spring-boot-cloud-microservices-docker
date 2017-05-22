package org.asion.account.domain.application;

import org.asion.account.domain.model.Account;

/**
 * @author Asion.
 * @since 16/5/29.
 */
public interface AccountManager {
    Account save(Account account);
    Account findOne(Long id);
    void delete(Long id);
    Iterable<Account> findAll();

    Account login(Account account);
}
