package org.asion.search.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.asion.search.ItemSearchManager;
import org.asion.search.repositories.ItemSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Search API service.
 * @author Asion.
 * @since 16/5/7.
 */
@Component("searchService")
public class ItemSearchManagerImpl implements ItemSearchManager {

    private final ItemSearchRepository itemSearchRepository;

    private final ObjectMapper objectMapper;

    @Autowired
    public ItemSearchManagerImpl(ItemSearchRepository itemSearchRepository, ObjectMapper objectMapper) {
        this.itemSearchRepository = itemSearchRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void search(String indices) {

    }
}
