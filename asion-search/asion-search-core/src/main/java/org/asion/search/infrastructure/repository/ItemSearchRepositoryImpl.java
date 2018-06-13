package org.asion.search.infrastructure.repository;

import org.asion.search.Item;
import org.asion.search.repository.ItemCustomizedSearchRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

/**
 * @author Asion.
 * @since 2018/6/11.
 */
@Repository
public class ItemSearchRepositoryImpl implements ItemCustomizedSearchRepository {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ItemSearchRepositoryImpl(ElasticsearchTemplate elasticsearchTemplate, JdbcTemplate jdbcTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Page<Item> search(String name) {

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withFilter(new BoolQueryBuilder().must(new TermQueryBuilder("name", name)))
                .build();

        return elasticsearchTemplate.queryForPage(searchQuery, Item.class);
    }

    public List<Item> findItems() {
        return jdbcTemplate.query("select * from item", (resultSet, i) -> {
            Item item = new Item();
            item.setId(resultSet.getLong("id"));
            item.setName(resultSet.getString("name"));
            item.setBrief(resultSet.getString("brief"));
            item.setSalePrice(resultSet.getDouble("sale_price"));
            item.setSaleLowPrice(resultSet.getDouble("sale_low_price"));
            item.setSaleHighPrice(resultSet.getDouble("sale_high_price"));
            item.setCreatedAt(resultSet.getDate("created_at"));
            item.setUpdatedAt(resultSet.getDate("updated_at"));
            return item;
        });

    }
}
