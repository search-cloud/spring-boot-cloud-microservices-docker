package org.asion.search.service;

import org.elasticsearch.client.Client;

/**
 * Interface for getting client for working with search engine
 * @author Asion.
 * @since 16/5/7.
 */
public interface SearchService {

    /**
     * Get Elasticsearch client API.
     * @return
     */
    Client getClient();

    /**
     * Add cluster node.
     * @param name
     */
    void addNewNode(String name);

    /**
     * Remove cluster node.
     * @param nodeName
     */
    void removeNode(String nodeName);

    void search(String indices);
}
