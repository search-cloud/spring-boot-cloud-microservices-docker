package org.asion.sample;

import org.mvnsearch.spring.boot.dubbo.EnableDubboConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @author Asion.
 * @since 16/4/29.
 */
@SpringBootApplication
@EnableDubboConfiguration("org.asion.sample.service")
@ComponentScan("org.asion.sample")
//@EnableDiscoveryClient
public class AsionSampleServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsionSampleServerApplication.class,args);
    }

    @Value("${spring.datasource.url}")
    private String url = "jdbc:mysql://192.168.99.100:2006/asion_sample?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
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
