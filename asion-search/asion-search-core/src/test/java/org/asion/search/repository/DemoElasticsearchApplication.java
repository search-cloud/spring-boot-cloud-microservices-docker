/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.asion.search.repository;

import org.asion.search.Item;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;

import static org.asion.search.common.UniqueIdentity.get13DigitsUId;

@SpringBootApplication
@EnableElasticsearchRepositories("org.asion.search.repository")
public class DemoElasticsearchApplication implements CommandLineRunner {

    /**
     * docker es x-pack 默认是打开的，所以需要认证
     *
     * @return TransportClient
     * @throws Exception 主要是地址异常
     */
    @Bean
    public TransportClient elasticsearchClient() throws Exception {
        return new PreBuiltXPackTransportClient(
                Settings.builder()
                        .put("cluster.name", "es-docker-cluster")
                        .put("xpack.security.user", "elastic:changeme")
                        .build())
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
    }

    @Autowired
    private ItemSearchRepository repository;

    private static Long id1 = get13DigitsUId();
    private static Long id2 = get13DigitsUId();

    @Override
    public void run(String... args) throws Exception {
        this.repository.deleteAll();
        saveItems();
        fetchAllItems();
        fetchIndividualItems();
        fetchItemById();
        fetchBySalePrice();
    }

    private void saveItems() {
        this.repository.save(new Item(id1, "Macbook Pro 13", "Apple Macbook Pro", 10888.0D));
        this.repository.save(new Item(get13DigitsUId(), "Macbook Pro 15", "Apple Macbook Pro", 15688.0D));
        this.repository.save(new Item(id2, "Iphone7 plus", "Apple Iphone7 plus", 6888.0D));
        this.repository.save(new Item(get13DigitsUId(), "Iphone8", "Apple Iphone8", 6888.0D));
        this.repository.save(new Item(get13DigitsUId(), "Ipad", "Apple Ipad", 4258.0D));
        this.repository.save(new Item(get13DigitsUId(), "Ipad", "Apple Ipad", 4258.0D));
        this.repository.save(new Item(get13DigitsUId(), "Ipad mini", "Apple Ipad mini", 3258.0D));
    }

    private void fetchAllItems() {
        System.out.println("Items found with findItems():");
        System.out.println("-------------------------------");
        for (Item item : this.repository.findAll()) {
            System.out.println(item);
        }
        System.out.println();
    }

    private void fetchIndividualItems() {
        System.out.println("Item found with findByName('Iphone8'):");
        System.out.println("--------------------------------");
        System.out.println(this.repository.findByName("Iphone8"));

        System.out.println("Items found with findByBriefContains('Ipad'):");
        System.out.println("--------------------------------");
        for (Item item : this.repository.findByBriefContains("Ipad")) {
            System.out.println(item);
        }
        System.out.println();
    }

    private void fetchItemById() {
        System.out.println("Item found with findById(" + id1 + "):");
        System.out.println("--------------------------------");
        System.out.println(this.repository.findById(id1));
        System.out.println();
    }

    private void fetchBySalePrice() {
        System.out.println("Item found with findBySalePriceGreaterThanEqual('6888.0'):");
        System.out.println("--------------------------------");
        System.out.println(this.repository.findBySalePriceGreaterThanEqual(6888.0D));
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoElasticsearchApplication.class, "--debug").close();
    }

}
