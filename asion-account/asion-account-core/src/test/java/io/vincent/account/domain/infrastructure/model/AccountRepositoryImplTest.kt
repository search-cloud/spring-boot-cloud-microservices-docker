package io.vincent.account.domain.infrastructure.model

import io.vincent.account.domain.model.Account
import io.vincent.account.domain.model.AccountRepository
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.unitils.dbunit.annotation.DataSet
import org.unitils.spring.annotation.SpringApplicationContext
import org.unitils.spring.annotation.SpringBeanByType

/**
 * @author Vincent.
 * @since 1.0, 16/5/1.
 */
@SpringApplicationContext("classpath:/app-context-unit.xml")
@DataSet("/database/dataset/asion_account.xml")
class AccountRepositoryImplTest {

    @SpringBeanByType
    private lateinit var accountRepository: AccountRepository

    @Test
    fun save() {
        // create
        val account = Account()
        account.code = "Test Text!"
        account.username = "Test Summary!"
        val created = accountRepository.create(account)
        assertThat(created).isNotNull.hasFieldOrPropertyWithValue(account.code, created.code)

        // update
        created.code = "Test Update Text!"
        created.username = "Test Update Summary!"
        val updated = accountRepository.update(created)
        assertThat(updated).isNotNull.hasFieldOrPropertyWithValue(account.code, updated.code)

    }

    @Test
    fun findOne() {
        val id = 1L
        val account = accountRepository.findOne(id)
        assertNotNull(account)
        assertEquals("This matches the test text1!", account.code)
    }

    @Test
    fun findById() {
        val id = 1L
        val optional = accountRepository.findById(id)
        assertThat(optional).isPresent
    }
}
