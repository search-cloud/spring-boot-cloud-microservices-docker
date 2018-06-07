package org.asion.search.server;

import org.asion.search.Item;
import org.asion.search.ItemSearchManager;
import org.asion.search.repository.ItemSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Search API service.
 * @author Asion.
 * @since 16/5/7.
 */
@Component
public class ItemSearchManagerImpl implements ItemSearchManager {

    private final ItemSearchRepository itemSearchRepository;

    @Autowired
    public ItemSearchManagerImpl(ItemSearchRepository itemSearchRepository) {
        this.itemSearchRepository = itemSearchRepository;
    }


    @Override
    public List<Item> findByName(String name) {
        return itemSearchRepository.findByName(name);
    }

    @Override
    public Page<Item> findByName(String name, Pageable pageable) {
        return itemSearchRepository.findByName(name, pageable);
    }

    @Override
    public List<Item> findByNameAndId(String name, Long id) {
        return findByNameAndId(name, id);
    }
}
