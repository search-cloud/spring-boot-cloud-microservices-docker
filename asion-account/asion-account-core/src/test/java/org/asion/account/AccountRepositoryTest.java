package org.asion.account;

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
@SpringApplicationContext("/appContext-unit.xml")
@DataSet(
        "/database/dataset/asion_user_user.xml"
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
        account.setText("Test Text!");
        account.setSummary("Test Summary!");
        Account created = accountRepository.save(account);
        assertNotNull(created);
        assertEquals(account.getSummary(), created.getSummary());

        // update
        created.setText("Test Update Text!");
        created.setSummary("Test Update Summary!");
        Account updated = accountRepository.save(created);
        assertNotNull(created);
        assertEquals(created.getSummary(), updated.getSummary());
    }

    @Test
    public void  findOne() {
        Long id = 1L;
        Account account = accountRepository.findOne(id);
        assertNotNull(account);
        assertEquals("This is the test text1!", account.getText());
    }

    @Test
    public void  delete() {
        Long id = 1L;
        accountRepository.delete(id);
    }
}
