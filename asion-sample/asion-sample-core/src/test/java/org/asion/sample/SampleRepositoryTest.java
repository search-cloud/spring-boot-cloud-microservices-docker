package org.asion.sample;

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
@ContextConfiguration(classes = org.asion.sample.common.SampleTestApplication.class)
public class SampleRepositoryTest extends UnitilsJUnit4 {

    @SpringBeanByType
    private SampleRepository sampleRepository;

    @Test
    public void findAll() {
        Iterable<Sample> resultList = sampleRepository.findAll();
        assertNotNull(resultList);
        assertTrue(resultList.iterator().hasNext());
    }

    @Test
    public void  save() {
        // create
        Sample sample = new Sample();
        sample.setText("Test Text!");
        sample.setSummary("Test Summary!");
        Sample created = sampleRepository.save(sample);
        assertNotNull(created);
        assertEquals(sample.getSummary(), created.getSummary());

        // update
        created.setText("Test Update Text!");
        created.setSummary("Test Update Summary!");
        Sample updated = sampleRepository.save(created);
        assertNotNull(created);
        assertEquals(created.getSummary(), updated.getSummary());
    }

    @Test
    public void  findOne() {
        Long id = 1L;
        Sample sample = sampleRepository.findOne(id);
        assertNotNull(sample);
        assertEquals("This is the test text1!", sample.getText());
    }

    @Test
    public void  delete() {
        Long id = 1L;
        sampleRepository.delete(id);
    }
}
