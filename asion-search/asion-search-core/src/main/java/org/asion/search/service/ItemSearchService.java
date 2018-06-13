package org.asion.search.service;

import org.asion.search.Item;

/**
 * @author Asion.
 * @since 2018/6/12.
 */
public interface ItemSearchService {
    Iterable<Item> fullIndices();
}
