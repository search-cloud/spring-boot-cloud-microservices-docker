package org.asion.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Asion
 * @since 16/4/30.
 */
public interface SearchSampleRepository extends ElasticsearchRepository<SearchSample, Long> {

	Iterable<SearchSample> findAll();

	SearchSample save(SearchSample sample);

	SearchSample findOne(Long id);

	void delete(Long id);

}
