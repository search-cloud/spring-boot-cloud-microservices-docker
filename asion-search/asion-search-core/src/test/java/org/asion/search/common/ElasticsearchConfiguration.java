package org.asion.search.common;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Asion.
 * @since 16/5/18.
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "org.asion.search")
@PropertySource(value = "classpath:/elasticsearch.properties")
@SuppressWarnings("all")
public class ElasticsearchConfiguration {

    @Value("${elasticsearch.port}") int port;
    @Value("${elasticsearch.host}") String hostName;

    @Bean
    public Client client() {
        Settings settings = Settings.settingsBuilder().put("client.transport.sniff", true).build();
        TransportClient client = TransportClient.builder().settings(settings).build();
        TransportAddress address = null;
        try {
            address = new InetSocketTransportAddress(InetAddress.getByName(hostName), port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        client.addTransportAddress(address);
        return client;
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(client());
    }

}
