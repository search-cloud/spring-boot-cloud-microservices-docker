package io.vincent.account.domain.infrastructure.model

import io.vincent.account.domain.infrastructure.model.mapper.AccountMapper
import io.vincent.account.domain.model.Account
import io.vincent.account.domain.model.AccountRepository
import io.vincent.common.vo.Page
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.util.*
import org.springframework.beans.BeanUtils
import com.github.pagehelper.PageInfo
import com.github.pagehelper.PageHelper



/**
 * @author Asion
 * @since 16/4/30.
 */
@Repository
open class AccountRepositoryImpl @Autowired
constructor(private val accountMapper: AccountMapper) : AccountRepository {
    override fun <S : Account> create(entity: S): S {
        accountMapper.create(entity)
        return entity
    }

    override fun <S : Account> update(entity: S): S {
        accountMapper.update(entity)
        return entity
    }

    override fun findById(id: Long): Optional<Account> = Optional.ofNullable(accountMapper.findById(id))

    override fun findOne(id: Long): Account? = accountMapper.findById(id)

    override fun findByUsername(username: String): Account? = accountMapper.findByUsername(username)

    override fun findPage(pageNumber: Int, pageSize: Int): Page<Account> {
        PageHelper.startPage<Account>(pageNumber, pageSize)
        val accountList = accountMapper.findAll()
        val pageInfo = PageInfo<Account>(accountList)
        val page = Page<Account>()
        BeanUtils.copyProperties(pageInfo, page)
        return page
    }

}
