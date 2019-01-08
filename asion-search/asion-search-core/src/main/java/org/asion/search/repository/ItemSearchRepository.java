package org.asion.search.repository;

import org.asion.search.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author Asion
 * @since 16/4/30.
 */
public interface ItemSearchRepository extends ElasticsearchRepository<Item, Long> {
    List<Item> findByName(String name);
    List<Item> findByBriefContains(String brief);
    Page<Item> findByName(String name, Pageable pageable);
    List<Item> findByNameAndId(String name, Long id);

    List<Item> findBySalePriceGreaterThanEqual(Double price);

}
