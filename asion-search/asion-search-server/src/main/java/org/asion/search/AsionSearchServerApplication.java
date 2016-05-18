package org.asion.search;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;

/**
 * @author Asion.
 * @since 16/4/29.
 */
@SpringBootApplication
@EnableAutoConfiguration()
@EnableWebMvc
@ComponentScan("org.asion.search")
public class AsionSearchServerApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(AsionSearchServerApplication.class,args);
    }

    @Value("${spring.datasource.url}")
    private String url;// = "jdbc:mysql://192.168.99.100:2006/asion_search?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
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

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/static/**")) {
            registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        }
        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry.addResourceHandler("/webjars/**").addResourceLocations(
                    "classpath:/META-INF/resources/webjars/");
        }
    }
}
