package org.asion.search;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Interface for getting client for working with search engine
 * @author Asion.
 * @since 16/5/7.
 */
public interface ItemSearchManager {
    List<Item> findByName(String name);
    List<Item> findByName(String name, int page, int pageSize);
    List<Item> findByNameAndId(String name, Long id);

    Page<Item> search(String name);

    Iterable<Item> fullIndices();
}
