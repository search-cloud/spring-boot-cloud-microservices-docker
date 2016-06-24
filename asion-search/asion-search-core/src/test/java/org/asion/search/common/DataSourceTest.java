package org.asion.search.common;

import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.database.annotations.TestDataSource;

import javax.sql.DataSource;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(defaultDataSource).isNotNull();
        try {
            assertThat(defaultDataSource.getConnection()).isNotNull();
            System.out.println(defaultDataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
