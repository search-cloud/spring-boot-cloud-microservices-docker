package org.asion.search.common;

import org.junit.Assert;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.database.annotations.TestDataSource;

import javax.sql.DataSource;

/**
 * @author Asion.
 * @since 16/5/1.
 */
public class DataSourceTest extends UnitilsJUnit4 {


    @TestDataSource
    private DataSource defaultDataSource;

//    @TestDataSource("database2")
//    private DataSource dataSource;

    @Test
    public void testCheckDefaultDataSource() {
        Assert.assertNotNull(defaultDataSource);
        System.out.println(defaultDataSource.getClass());
    }
}
