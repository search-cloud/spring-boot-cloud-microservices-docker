package org.asion.bot.parser

import org.openqa.selenium.WebDriver

/**
 *
 * @author Asion.
 * @since 2018/5/4.
 */
interface PhantomJSParser<T> {
    fun parse(url: String, html: String, item: T, driver: WebDriver): T
}