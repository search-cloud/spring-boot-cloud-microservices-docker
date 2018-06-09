package org.asion.search.controller

import org.asion.search.Item
import org.asion.search.ItemSearchManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
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
    fun searchByName(@PathVariable name: String): ResponseEntity<List<Item>> {
        return ResponseEntity.ok(itemSearchManager.findByName(name))
    }
}
