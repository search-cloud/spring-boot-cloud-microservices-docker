package org.asion.bot.domain.model

import org.asion.bot.CaptureItem

/**
 *
 * @author Asion.
 * @since 2018/5/3.
 */
interface CaptureItemRepository : BaseRepository<CaptureItem, Long> {
    fun saveCaptureItems(itemList: List<CaptureItem>): List<Long>

    fun findAll(): List<CaptureItem>
}