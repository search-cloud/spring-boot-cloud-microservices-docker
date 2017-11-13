package org.asion.account.common;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @author Asion.
 * @since 16/5/1.
 */
@SpringBootApplication
@ComponentScan(value = "org.asion.account")
public class AccountTestApplication {

    @Bean
    public DataSource dataSource() {
        String url = "jdbc:mysql://db.asion.org:2006/asion_account?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
        String user = "root";
        String password = "asion";
        DriverManagerDataSource dataSource = new DriverManagerDataSource(url, user, password);
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return dataSource;
    }

}
