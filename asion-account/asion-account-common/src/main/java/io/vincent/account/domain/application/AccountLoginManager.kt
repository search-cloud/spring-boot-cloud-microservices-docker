package io.vincent.account.domain.application

import io.vincent.account.domain.model.Account
import org.asion.base.ddd.application.RemoteFacade

/**
 * 账号登陆相关接口。
 *
 * @author Vincent
 * @since 1.0, 11/2/18
 */
@RemoteFacade
interface AccountLoginManager {
    fun login(account: Account): Account
}