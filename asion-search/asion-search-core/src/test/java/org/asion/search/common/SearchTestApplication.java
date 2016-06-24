package org.asion.search.common;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @author Asion.
 * @since 16/5/1.
 */
@SpringBootApplication
@ComponentScan(value = "org.asion.search")
@EnableAutoConfiguration(exclude = ElasticsearchConfiguration.class)
public class SearchTestApplication {

    @Bean
    public DataSource dataSource() {
        String url = "jdbc:mysql://192.168.99.100:1006/asion_search?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
        String user = "root";
        String password = "asion";
        DriverManagerDataSource dataSource = new DriverManagerDataSource(url, user, password);
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

}
