package org.asion.search;

import org.asion.search.common.ElasticsearchConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Asion.
 * @since 16/5/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ElasticsearchConfiguration.class)
public class SearchSampleRepositoryTest {

    @Autowired
    private SearchSampleRepository searchSampleRepository;

    @Before
    public void emptyData(){
        searchSampleRepository.deleteAll();
    }

    @Test
    public void shouldReturnListOfProductsByName() {
        //given
        SearchSample searchSample = new SearchSample();
        searchSample.setId(1L);
        searchSample.setSummary("test product 1");
        searchSample.setText("How great would it be if we could search for this product.");
        searchSampleRepository.index(searchSample);

        searchSample = new SearchSample();
        searchSample.setId(2L);
        searchSample.setSummary("test Product 2");
        searchSample.setText("How great would it be if we could search for this other product.");
        searchSampleRepository
                .index(searchSample);
        //when
        List<SearchSample> products = searchSampleRepository.findByName("product");
        //then
        assertThat(products.size(), is(2));
    }

    @Test
    public void shouldReturnListOfBookByNameWithPageable(){
        //given
        SearchSample searchSample = new SearchSample();
        searchSample.setId(1L);
        searchSample.setSummary("test product 1");
        searchSample.setText("How great would it be if we could search for this product.");
        searchSampleRepository.index(searchSample);

        searchSample = new SearchSample();
        searchSample.setId(2L);
        searchSample.setSummary("test Product 2");
        searchSample.setText("How great would it be if we could search for this other product.");
        searchSampleRepository
                .index(searchSample);
        //when
        List<SearchSample> products = searchSampleRepository.findByName("product", new PageRequest(0,1));
        //then
        assertThat(products.size(), is(1));
    }

    @Test
    public void shouldReturnListOfProductsForGivenNameAndId(){
        //given
        SearchSample searchSample = new SearchSample();
        searchSample.setId(1L);
        searchSample.setSummary("test product 1");
        searchSample.setText("How great would it be if we could search for this product.");
        searchSampleRepository.save(searchSample);

        searchSample = new SearchSample();
        searchSample.setId(2L);
        searchSample.setSummary("test Product 2");
        searchSample.setText("How great would it be if we could search for this other product.");
        searchSampleRepository
                .save(searchSample);

        List<SearchSample> products = searchSampleRepository.findByNameAndId("product", 1L);

        //then
        assertThat(products.size(),is(1));
    }
}
