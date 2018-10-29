package org.asion.search.common;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;

/**
 * @author Asion.
 * @since 16/5/1.
 */
@SpringBootApplication
@ComponentScan("org.asion.search")
@EnableElasticsearchRepositories("org.asion.search.repository")
public class SearchTestApplication implements EnvironmentAware {

    private Environment environment;

    /**
     * docker es x-pack 默认是打开的，所以需要认证
     *
     * @return TransportClient
     * @throws Exception 主要是地址异常
     */
    @Bean
    public TransportClient elasticsearchClient() throws Exception {
//        TransportClientFactoryBean factory = new TransportClientFactoryBean();
//        factory.setClusterNodes(this.properties.getClusterNodes());
//        factory.setProperties(createProperties());
//        factory.afterPropertiesSet();
//        return factory.getObject();
        return new PreBuiltXPackTransportClient(
                Settings.builder()
                        .put("cluster.name", environment.getProperty("spring.data.elasticsearch.cluster-name"))
                        .put("xpack.security.user", environment.getProperty("xpack.security.user"))
                        .build())
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
        //.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9301));
    }

    @Override
    public void setEnvironment(@NotNull Environment environment) {
        this.environment = environment;
    }
}
