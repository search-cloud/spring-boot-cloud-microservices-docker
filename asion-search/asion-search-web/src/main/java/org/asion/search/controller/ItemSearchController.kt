package org.asion.search.controller

import org.asion.search.Item
import org.asion.search.ItemSearchManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * @author Asion.
 * @since 2018/6/8.
 */
@RestController
@RequestMapping("/items/")
class ItemSearchController @Autowired
constructor(private val itemSearchManager: ItemSearchManager) {

    @RequestMapping("/{name}")
    fun findByName(@PathVariable name: String): ResponseEntity<List<Item>> {
        return ResponseEntity.ok(itemSearchManager.findByName(name))
    }

    @RequestMapping("/{name}/{pageNumber}")
    fun findPageByName(@PathVariable name: String, @PathVariable pageNumber: Int): ResponseEntity<List<Item>> {
        return ResponseEntity.ok(itemSearchManager.findByName(name, pageNumber, 50))
    }

    @RequestMapping("/_q")
    fun searchByName(@RequestParam name: String): ResponseEntity<Page<Item>> {
        return ResponseEntity.ok(itemSearchManager.search(name))
    }

    @RequestMapping("/indices")
    fun indices(): ResponseEntity<Iterable<Item>> {
        return ResponseEntity.ok(itemSearchManager.fullIndices())
    }
}
