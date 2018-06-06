package org.asion.search;

import org.asion.search.repositories.ItemSearchRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Asion.
 * @since 16/5/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ItemSearchRepositoryTest {

    @Autowired
    private ItemSearchRepository itemSearchRepository;

    @Before
    public void emptyData(){
        itemSearchRepository.deleteAll();
    }

    @Test
    public void shouldReturnListOfProductsByName() {
        //given
        Item item = new Item();
        item.setId(1L);
        item.setName("test product 1");
        item.setBrief("How great would it be if we could search for this product.");
        itemSearchRepository.index(item);

        item = new Item();
        item.setId(2L);
        item.setName("test Product 2");
        item.setBrief("How great would it be if we could search for this other product.");
        itemSearchRepository
                .index(item);
        //when
        List<Item> products = itemSearchRepository.findByName("product");
        //then
        assertThat(products.size(), is(2));
    }

    @Test
    public void shouldReturnListOfBookByNameWithPageable(){
        //given
        Item item = new Item();
        item.setId(1L);
        item.setName("test product 1");
        item.setBrief("How great would it be if we could search for this product.");
        itemSearchRepository.index(item);

        item = new Item();
        item.setId(2L);
        item.setName("test Product 2");
        item.setBrief("How great would it be if we could search for this other product.");
        itemSearchRepository
                .index(item);
        //when
        List<Item> products = itemSearchRepository.findByName("product", PageRequest.of(0,1));
        //then
        assertThat(products.size(), is(1));
    }

    @Test
    public void shouldReturnListOfProductsForGivenNameAndId(){
        //given
        Item item = new Item();
        item.setId(1L);
        item.setName("test product 1");
        item.setBrief("How great would it be if we could search for this product.");
        itemSearchRepository.save(item);

        item = new Item();
        item.setId(2L);
        item.setName("test Product 2");
        item.setBrief("How great would it be if we could search for this other product.");
        itemSearchRepository
                .save(item);

        List<Item> products = itemSearchRepository.findByNameAndId("product", 1L);

        //then
        assertThat(products.size(),is(1));
    }
}
