package org.asion.demo.domain.model;

import org.asion.base.ddd.domain.BaseRepository;
import org.asion.base.ddd.domain.annotations.DomainRepository;

/**
 * @author Asion.
 * @since 2017/3/20.
 */
@DomainRepository
public interface AccountRepository extends BaseRepository<Long, Account> {
}
