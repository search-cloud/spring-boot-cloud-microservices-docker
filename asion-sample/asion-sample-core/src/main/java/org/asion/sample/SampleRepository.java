package org.asion.sample;

/**
 * @author Asion
 * @since 16/4/30.
 */
public interface SampleRepository {

	Iterable<Sample> findAll();

	Sample save(Sample sample);

	Sample findOne(Long id);

	void delete(Long id);

}
