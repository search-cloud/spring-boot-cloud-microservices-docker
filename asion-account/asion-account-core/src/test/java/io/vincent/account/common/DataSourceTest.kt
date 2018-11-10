package io.vincent.account.common

import org.junit.Assert
import org.junit.Test
import org.unitils.UnitilsJUnit4
import org.unitils.database.annotations.TestDataSource

import javax.sql.DataSource

/**
 * @author Asion.
 * @since 16/5/1.
 */
class DataSourceTest : UnitilsJUnit4() {


    @TestDataSource
    private val defaultDataSource: DataSource? = null

//    @TestDataSource("database2")
//    private val dataSource: DataSource? = null

    @Test
    fun testCheckDefaultDataSource() {
        Assert.assertNotNull(defaultDataSource)
        println(defaultDataSource!!.javaClass)
    }
}
