package org.asion.account.domain.model;

import org.asion.base.ddd.domain.BaseRepository;
import org.asion.base.ddd.domain.annotations.DomainRepository;

/**
 * @author Asion
 * @since 16/4/30.
 */
@DomainRepository
public interface AccountRepository extends BaseRepository<Long, Account> {

    Iterable<Account> findAll();

    Account save(Account account);

}
