package io.vincent.account.domain.application

import io.vincent.account.domain.model.Account
import org.asion.base.ddd.application.RemoteFacade

/**
 * Created by Vincent on 11/2/18.
 * @author Vincent
 * @since 1.0, 11/2/18
 */
@RemoteFacade
interface AccountRegisterManager {
    fun register(account: Account): Account
}