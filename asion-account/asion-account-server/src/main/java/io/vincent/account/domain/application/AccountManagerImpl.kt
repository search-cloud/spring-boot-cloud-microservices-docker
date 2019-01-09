package io.vincent.account.domain.application

import com.alibaba.dubbo.config.annotation.Service
import io.vincent.account.domain.model.Account
import io.vincent.account.domain.model.AccountRepository
import io.vincent.account.domain.service.AccountService
import io.vincent.common.vo.Page
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

/**
 * @author Asion.
 * @since 16/8/24.
 */
@Component
//@DubboService(interfaceClass = AccountManager.class)
@Service(version = "1.0.0",
        application = "\${dubbo.application.id}",
        protocol = ["\${dubbo.protocol.id}"],
        registry = ["\${dubbo.registry.id}"]
)
class AccountManagerImpl @Autowired
constructor(private val accountRepository: AccountRepository,
            private val accountService: AccountService) : AccountManager {

    override fun create(account: Account): Account {
        return accountRepository.create(account)
    }

    override fun update(account: Account): Account {
        return accountRepository.update(account)
    }

    override fun findOne(id: Long): Account? {
        return accountRepository.findOne(id)
    }

    override fun findById(id: Long): Optional<Account> {
        return accountRepository.findById(id)
    }

    override fun findByUsename(username: String): Account? {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return null
    }

    override fun findByMobile(mobile: String): Account? {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return null
    }

    override fun findByEmail(email: String): Account? {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return null
    }

    override fun findPage(pageNumber: Int, pageSize: Int): Page<Account> {
        return accountRepository.findPage(pageNumber, pageSize)
    }

    override fun disable(id: Long): Long? = accountService.disable(id)
}
