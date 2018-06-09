package org.asion.search.server

import org.asion.search.Item
import org.asion.search.ItemSearchManager
import org.asion.search.repository.ItemSearchRepository
import org.mvnsearch.spring.boot.dubbo.EnableDubboConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

/**
 * Search API service.
 * @author Asion.
 * @since 16/5/7.
 */
@Component
@EnableDubboConfiguration
class ItemSearchManagerImpl @Autowired
constructor(private val itemSearchRepository: ItemSearchRepository) : ItemSearchManager {


    override fun findByName(name: String): List<Item> {
        return itemSearchRepository.findByName(name)
    }

    override fun findByName(name: String, pageable: Pageable): Page<Item> {
        return itemSearchRepository.findByName(name, pageable)
    }

    override fun findByNameAndId(name: String, id: Long?): List<Item> {
        return itemSearchRepository.findByNameAndId(name, id)
    }
}
