package org.asion.search.repository;

import org.asion.search.Item;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Asion
 * @since 16/4/30.
 */
public interface ItemCustomizedSearchRepository {
    Page<Item> search(String name);
    List<Item> findItems();
}
