package org.asion.bot.test.parser

import org.asion.bot.parser.ItemListParser
import org.jsoup.Jsoup
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.io.InputStream

/**
 * @author Asion.
 * @since 2018/5/16.
 */
class ItemListParserTest {

    @Test
    fun parse() {

    }

    @Test
    fun parseCategories() {
        val document = Jsoup.parse(inputStream, "GBK", "")
        val categories = ItemListParser().parseCategories(document)
        println(categories)
    }

    @Test
    fun filterPageNo() {
    }

    @Test
    fun getCurrentPageNo() {
    }


    private var inputStream: InputStream? = null

    @Before
    @Throws(IOException::class)
    fun beforeClass() {
        inputStream = ClassLoader.getSystemResourceAsStream("test-list.html")
    }
}