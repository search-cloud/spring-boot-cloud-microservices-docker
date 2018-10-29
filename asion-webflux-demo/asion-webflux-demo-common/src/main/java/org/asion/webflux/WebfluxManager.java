package org.asion.webflux;

/**
 * @author Asion.
 * @since 16/5/29.
 */
public interface WebfluxManager {
    Webflux save(Webflux sample);
    Webflux findOne(Long id);
    void delete(Long id);
    Iterable<Webflux> findAll();
}