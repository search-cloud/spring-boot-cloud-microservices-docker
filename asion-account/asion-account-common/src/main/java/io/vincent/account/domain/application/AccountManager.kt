package io.vincent.account.domain.application

import io.vincent.account.domain.model.Account
import io.vincent.common.vo.Page
import org.asion.base.ddd.application.RemoteFacade
import java.util.*

/**
 * 账号基础接口。
 *
 * @author Asion.
 * @since 16/5/29.
 */
@RemoteFacade
interface AccountManager {
    fun create(account: Account): Account
    fun update(account: Account): Account
    fun findOne(id: Long): Account?
    fun findById(id: Long): Optional<Account>
    fun findPage(pageNumber: Int, pageSize: Int): Page<Account>
}
