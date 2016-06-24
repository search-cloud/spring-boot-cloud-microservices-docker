package org.asion.search;

/**
 * @author Asion
 * @since 16/4/30.
 */
public interface SearchSampleRepository /*extends ElasticsearchRepository<SearchSample, Long>*/ {

	Iterable<SearchSample> findAll();

	SearchSample save(SearchSample sample);

	SearchSample findOne(Long id);

	void delete(Long id);

}
