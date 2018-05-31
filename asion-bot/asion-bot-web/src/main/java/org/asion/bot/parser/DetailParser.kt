package org.asion.bot.parser

import org.asion.bot.CaptureItem

/**
 *
 * @author Asion.
 * @since 2018/5/15.
 */
interface DetailParser : Parser<CaptureItem> {

    override fun parse(html: String): List<CaptureItem> {
        return arrayListOf(parse(html, CaptureItem()))
    }

    fun parse(html: String, item: CaptureItem): CaptureItem
}