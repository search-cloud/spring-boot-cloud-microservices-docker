package org.asion.sample;

/**
 * @author Asion.
 * @since 16/5/29.
 */
public interface SampleManager {
    Sample save(Sample sample);
    Sample findOne(Long id);
    void delete(Long id);
    Iterable<Sample> findAll();
}
