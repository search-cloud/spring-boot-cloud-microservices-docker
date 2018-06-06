package org.asion.webflux;

import org.mvnsearch.spring.boot.dubbo.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Asion.
 * @since 16/4/29.
 */
@SpringBootApplication
@EnableDubboConfiguration("org.asion.webflux.service")
@ComponentScan("org.asion.webflux")
@EnableEurekaClient
public class AsionWebfluxServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsionWebfluxServerApplication.class,args);
    }

}
