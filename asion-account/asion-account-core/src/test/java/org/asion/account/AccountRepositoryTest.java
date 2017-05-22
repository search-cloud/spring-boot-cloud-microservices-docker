package org.asion.account;

import org.asion.account.domain.model.Account;
import org.asion.account.domain.model.AccountRepository;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

import static org.junit.Assert.*;

/**
 * @author Asion.
 * @since 16/5/1.
 */
@SpringApplicationContext("classpath:/app-context-unit.xml")
@DataSet(
        "/database/dataset/asion_account.xml"
)
public class AccountRepositoryTest extends UnitilsJUnit4 {

    @SpringBeanByType
    private AccountRepository accountRepository;

    @Test
    public void findAll() {
        Iterable<Account> resultList = accountRepository.findAll();
        assertNotNull(resultList);
        assertTrue(resultList.iterator().hasNext());
    }

    @Test
    public void  save() {
        // create
        Account account = new Account();
        account.setCode("Test Text!");
        account.setName("Test Summary!");
        Account created = accountRepository.save(account);
        assertNotNull(created);
        assertEquals(account.getCode(), created.getCode());

        // update
        created.setCode("Test Update Text!");
        created.setName("Test Update Summary!");
        Account updated = accountRepository.save(created);
        assertNotNull(created);
        assertEquals(created.getCode(), updated.getCode());
    }

    @Test
    public void  findOne() {
        Long id = 1L;
        Account account = accountRepository.findOne(id);
        assertNotNull(account);
        assertEquals("This is the test text1!", account.getCode());
    }

    @Test
    public void  delete() {
        Long id = 1L;
        accountRepository.delete(id);
    }
}
