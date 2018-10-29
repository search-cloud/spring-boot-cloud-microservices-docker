package org.asion.search;

import org.junit.Test;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Asion.
 * @since 16/5/18.
 */
@SpringApplicationContext("/appContext-unit.xml")
public class ItemSearchServiceImplTest extends UnitilsJUnit4 {

    @SpringBeanByType
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void testElasticsearchTemplate() {
        assertThat(elasticsearchTemplate).isNotNull();
    }

    @Test
    public void test() {
        assertThat(elasticsearchTemplate.getClient()).isNotNull();
    }
}
