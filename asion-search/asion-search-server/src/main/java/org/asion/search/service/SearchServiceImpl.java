package org.asion.search.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

/**
 * Search API service.
 * @author Asion.
 * @since 16/5/7.
 */
@Component("searchService")
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Client getClient() {
        return elasticsearchTemplate.getClient();
    }

    @Override
    public void addNewNode(String name) {
        // TODO implements addNewNode
    }

    @Override
    public void removeNode(String nodeName) {
        // TODO implements removeNode
    }

    @Override
    public void search(String indices) {
        SearchRequest searchRequest = new SearchRequest(indices);
        ActionFuture<SearchResponse> search = getClient().search(searchRequest);
        SearchResponse searchResponse = search.actionGet();
        SearchHits hits = searchResponse.getHits();
        hits.forEach(hit -> {
            try {
                String fields = objectMapper.writeValueAsString(hit.getFields());
                System.out.println(fields);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }
}
