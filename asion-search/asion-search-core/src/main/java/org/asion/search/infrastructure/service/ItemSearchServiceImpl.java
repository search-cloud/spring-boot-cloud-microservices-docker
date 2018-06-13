package org.asion.search.infrastructure.service;

import org.asion.search.Item;
import org.asion.search.repository.ItemCustomizedSearchRepository;
import org.asion.search.repository.ItemSearchRepository;
import org.asion.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Asion.
 * @since 2018/6/12.
 */
@Service
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private ItemSearchRepository itemSearchRepository;
    @Autowired
    private ItemCustomizedSearchRepository itemCustomizedSearchRepository;

    @Override
    public Iterable<Item> fullIndices() {
        List<Item> items = itemCustomizedSearchRepository.findItems();
        return itemSearchRepository.saveAll(items);
    }
}
