package org.asion.bot


/**
 * @author Asion.
 * @since 2018/5/3.
 */
interface CaptureItemManger {
    fun save(itemList: List<CaptureItem>): List<Long>
    fun save(captureItem: CaptureItem): Long
}
