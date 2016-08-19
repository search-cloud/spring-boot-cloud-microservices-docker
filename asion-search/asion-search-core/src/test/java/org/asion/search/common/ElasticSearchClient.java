//package org.asion.search.common;
//
//import org.elasticsearch.client.Client;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.ImmutableSettings;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
//
//import java.net.InetAddress;
//
//import static org.elasticsearch.common.lang3.StringUtils.substringAfterLast;
//import static org.elasticsearch.common.lang3.StringUtils.substringBeforeLast;
//
//@Configuration
//@EnableElasticsearchRepositories(
//        basePackages =
//        {
//                "org.springframework.data.elasticsearch.repositories.sample", "org.springframework.data.elasticsearch.config", "com/paraphrase/ps2"
//        }
//        , considerNestedRepositories = true)
//public class ElasticSearchClient {
//    @Autowired
//    private ElasticSerachConfig esConfig;
//    static final String COLON = ":";
//    static final String COMMA = ",";
//
//    private Settings getSetting() {
//        return ImmutableSettings.settingsBuilder().put("client.transport.sniff", esConfig.isClusterSniff()).put("client.transport.ping_timeout", esConfig.getClientPingTimeout()).put("client.transport.nodes_sampler_interval", esConfig.getClientNodesSampleInterval()).put("cluster.name", esConfig.getClusterName()).build();
////        return Settings.builder().put("client.transport.sniff", esConfig.isClusterSniff()).put("client.transport.ping_timeout", esConfig.getClientPingTimeout()).put("client.transport.nodes_sampler_interval", esConfig.getClientNodesSampleInterval()).put("cluster.name", esConfig.getClusterName()).build();
//    }
//
//    protected Client buildClient() throws Exception {
//        TransportClient client = new TransportClient(getSetting());
////        TransportClient client = TransportClient.builder().settings(getSetting()).build();
//        try {
//            for (String clusterNode : split(esConfig.getClusteNodes(), COMMA)) {
//                String hostName = substringBeforeLast(clusterNode, COLON);
//                String port = substringAfterLast(clusterNode, COLON);
//                System.out.println("Available hostnames " + hostName + " port: " + port);
//                try {
//                    client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostName), Integer.valueOf(port)));
//                } catch (Exception ex) {
//                    logger.error("Unable to connect to elastic search node", ex);
//                    continue;
//                }
//            }
//            client.connectedNodes();
//        } catch (Exception ex) {
//            logger.error("Unable to connect to elastic search nodes", ex);
//        }
//        return client;
//    }
//
//    @Bean(name = "elasticsearchTemplate")
//    public ElasticsearchTemplate getElasticSearchTenplate() throws Exception {
//        return new ElasticsearchTemplate(buildClient());
//    }
//}