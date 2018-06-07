package org.asion.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface for getting client for working with search engine
 * @author Asion.
 * @since 16/5/7.
 */
public interface ItemSearchManager {
    List<Item> findByName(String name);
    Page<Item> findByName(String name, Pageable pageable);
    List<Item> findByNameAndId(String name, Long id);
}
