package io.vincent.account.domain.model

import io.vincent.common.vo.Page
import org.asion.base.ddd.domain.BaseRepository
import org.asion.base.ddd.domain.annotations.DomainRepository

/**
 * @author Asion
 * @since 16/4/30.
 */
@DomainRepository
interface AccountRepository : BaseRepository<Account, Long> {
    fun findByUsername(username: String): Account?
    fun findPage(pageNumber: Int, pageSize: Int): Page<Account>
}
