package org.asion.bot.parser

/**
 *
 * @author Asion.
 * @since 2018/5/4.
 */
interface Parser<T> {
    fun parse(html: String): List<T>
}