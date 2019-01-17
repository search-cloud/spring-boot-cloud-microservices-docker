package io.vincent.account;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.DispatcherType;
import java.util.*;

import static springfox.documentation.builders.PathSelectors.any;

/**
 * @author Asion.
 * @since 16/8/24.
 */
@SpringBootApplication
@EnableWebMvc
@EnableAsync
@EnableSwagger2
public class AsionAccountWebApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(AsionAccountWebApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean siteMeshFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new ConfigurableSiteMeshFilter() {
            @Override
            protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
                // "/jsondoc-ui.html" 这个请求不使用模板页
//                builder.addExcludedPath("/jsondoc-ui.html");
                // 在"/*"下面所有的页面都使用模板页"/static/layout.html"
                builder.addDecoratorPath("/*", "/static/layout.html");
                // swagger 单独渲染
                builder.addExcludedPath("/swagger-ui.html");
            }
        });
        filterRegistrationBean.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        return filterRegistrationBean;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        if (!registry.hasMappingForPattern("/static/**")) {
            registry.addResourceHandler("/static/**")
                    .addResourceLocations("classpath:/static/");
        }
        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultKaptcha defaultKaptcha(){
        DefaultKaptcha defaultKaptcha=new DefaultKaptcha();
        Properties properties=new Properties();
        properties.setProperty("kaptcha.border", "no");
        properties.setProperty("kaptcha.border.color", "105,179,90");
        properties.setProperty("kaptcha.textproducer.font.color", "51,51,51");
        properties.setProperty("kaptcha.image.width", "120");
        properties.setProperty("kaptcha.image.height", "60");
        properties.setProperty("kaptcha.textproducer.font.size","40");
        properties.setProperty("kaptcha.session.key", "code");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        properties.setProperty("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.WaterRipple");
        Config config=new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

    private static final Contact DEFAULT_CONTACT = new Contact("Vincent.Lu", "http://seekheap.com/about", "luxuexian99@gmail.com");

    private static final ApiInfo DEFAULT_API_INFO = metaData();

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(Collections.singletonList("application/json")); // "application/xml"

    /**
     * Default api Configuration.
     * @return swagger Docket.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                       .apiInfo(DEFAULT_API_INFO)
//                       .produces(DEFAULT_PRODUCES_AND_CONSUMES)
//                       .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
//                       .pathMapping("/accounts")
                       .select()
                       .apis(RequestHandlerSelectors.basePackage("io.vincent.account.restful"))
                       .paths(any())
                       .build();
    }

    /**
     * Default meta data.
     * @return swagger ApiInfo.
     */
    private static ApiInfo metaData() {
        return new ApiInfoBuilder()
                       .title("Vincent Account REST API")
                       .description("Vincent Account REST API for Online Store")
                       .termsOfServiceUrl("http://www.seekheap.com")
                       .contact(DEFAULT_CONTACT)
                       .license("Apache License Version 2.0")
                       .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                       .version("1.0.0")
                       .build();
    }

}
