package io.vincent.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Asion.
 * @since 16/8/24.
 */
@SpringBootApplication
//@EnableDubboConfiguration("io.vincent.account.domain.application")
@MapperScan("io.vincent.account.domain.infrastructure")
@EnableWebMvc
public class AsionAccountServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsionAccountServerApplication.class,args);
    }

}
