package org.asion.bot.common;

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
@ComponentScan(value = "org.asion.bot")
public class SampleTestApplication {

    @Bean
    public DataSource dataSource() {
        String url = "jdbc:mysql://localhost:2006/asion_sample?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
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
