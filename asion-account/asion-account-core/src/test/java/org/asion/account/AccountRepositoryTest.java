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
        Iterable<User> resultList = accountRepository.findAll();
        assertNotNull(resultList);
        assertTrue(resultList.iterator().hasNext());
    }

    @Test
    public void  save() {
        // create
        user user = new user();
        user.setText("Test Text!");
        user.setSummary("Test Summary!");
        user created = accountRepository.save(user);
        assertNotNull(created);
        assertEquals(user.getSummary(), created.getSummary());

        // update
        created.setText("Test Update Text!");
        created.setSummary("Test Update Summary!");
        user updated = accountRepository.save(created);
        assertNotNull(created);
        assertEquals(created.getSummary(), updated.getSummary());
    }

    @Test
    public void  findOne() {
        Long id = 1L;
        user user = accountRepository.findOne(id);
        assertNotNull(user);
        assertEquals("This is the test text1!", user.getText());
    }

    @Test
    public void  delete() {
        Long id = 1L;
        accountRepository.delete(id);
    }
}
