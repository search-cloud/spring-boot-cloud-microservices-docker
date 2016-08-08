package org.asion.search;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author Asion
 * @since 16/4/30.
 */
public interface SearchSampleRepository extends ElasticsearchRepository<SearchSample, Long> {
    List<SearchSample> findByName(String summary);
    List<SearchSample> findByName(String summary, Pageable pageable);
    List<SearchSample> findByNameAndId(String summary, Long id);
}
