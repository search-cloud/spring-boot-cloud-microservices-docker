package org.asion.search.server

import com.alibaba.dubbo.config.annotation.DubboService
import org.asion.search.Item
import org.asion.search.ItemSearchManager
import org.asion.search.repository.ItemCustomizedSearchRepository
import org.asion.search.repository.ItemSearchRepository
import org.asion.search.service.ItemSearchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

/**
 * Search API service.
 * @author Asion.
 * @since 16/5/7.
 */
@Component
@DubboService
class ItemSearchManagerImpl @Autowired
constructor(private val itemSearchRepository: ItemSearchRepository,
            private val itemCustomizedSearchRepository: ItemCustomizedSearchRepository,
            private val itemSearchService: ItemSearchService) : ItemSearchManager {
    override fun findByName(name: String): List<Item> = itemSearchRepository.findByName(name)

    override fun findByName(name: String, page: Int, pageSize: Int): List<Item> =
            itemSearchRepository.findByName(name, PageRequest.of(page, pageSize))


    override fun findByNameAndId(name: String, id: Long?): List<Item> = itemSearchRepository.findByNameAndId(name, id)

    override fun search(name: String): Page<Item> = itemCustomizedSearchRepository.search(name)

    override fun fullIndices(): Iterable<Item> = itemSearchService.fullIndices()

}
