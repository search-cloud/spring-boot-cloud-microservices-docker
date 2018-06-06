package org.asion.webflux;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
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
        "/database/dataset/asion_sample_sample.xml"
)
@ContextConfiguration(classes = org.asion.webflux.common.WebfluxTestApplication.class)
public class WebfluxRepositoryTest extends UnitilsJUnit4 {

    @SpringBeanByType
    private WebfluxRepository webfluxRepository;

    @Test
    public void findAll() {
        Iterable<Webflux> resultList = webfluxRepository.findAll();
        assertNotNull(resultList);
        assertTrue(resultList.iterator().hasNext());
    }

    @Test
    public void  save() {
        // create
        Webflux sample = new Webflux();
        sample.setText("Test Text!");
        sample.setSummary("Test Summary!");
        Webflux created = webfluxRepository.save(sample);
        assertNotNull(created);
        assertEquals(sample.getSummary(), created.getSummary());

        // update
        created.setText("Test Update Text!");
        created.setSummary("Test Update Summary!");
        Webflux updated = webfluxRepository.save(created);
        assertNotNull(created);
        assertEquals(created.getSummary(), updated.getSummary());
    }

    @Test
    public void  findOne() {
        Long id = 1L;
        Webflux sample = webfluxRepository.findOne(id);
        assertNotNull(sample);
        assertEquals("This is the test text1!", sample.getText());
    }

    @Test
    public void  delete() {
        Long id = 1L;
        webfluxRepository.delete(id);
    }
}
