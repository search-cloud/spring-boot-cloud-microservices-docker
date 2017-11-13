package org.asion.account;

import org.mvnsearch.spring.boot.dubbo.EnableDubboConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @author Asion.
 * @since 16/8/24.
 */
@SpringBootApplication
@EnableDubboConfiguration("org.asion.account.service")
@ComponentScan("org.asion.account")
@EnableDiscoveryClient
public class AsionAccountServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsionAccountServerApplication.class,args);
    }

    @Value("${spring.datasource.url}")
    private String url = "jdbc:mysql://db.asion.org:2006/asion_user?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
    @Value("${spring.datasource.username}")
    String user = "root";
    @Value("${spring.datasource.password}")
    String password = "asion";
    @Value("${spring.datasource.driverClassName}")
    String driverClassName = "com.mysql.jdbc.Driver";

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(url, user, password);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }

    /**
     * <pre> {@code
     *  <bean id="jdbcTemplate"
     *      class="org.springframework.jdbc.core.JdbcTemplate" abstract="false"
     *      lazy-init="false" autowire="default" dependency-check="default">
     *      <property name="dataSource" ref="dataSource" />
     *  </bean>
     * } </pre>
     * @return JdbcTemplate
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
