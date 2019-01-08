package io.vincent.account.domain.infrastructure.service

import io.vincent.account.domain.model.Account
import io.vincent.account.domain.model.AccountRepository
import io.vincent.account.domain.service.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by Vincent on 1/7/19.
 * @author Vincent
 * @since 1.0, 1/7/19
 */
@Service
class AccountServiceImpl @Autowired
constructor(private val accountRepository: AccountRepository): AccountService {
    override fun disable(id: Long): Long? = accountRepository.updateStatus(id, Account.Status.DISABLE.code)
}