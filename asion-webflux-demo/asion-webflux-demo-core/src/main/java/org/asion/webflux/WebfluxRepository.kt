package org.asion.webflux;

/**
 * @author Asion
 * @since 16/4/30.
 */
interface WebfluxRepository {

    fun findAll(): Iterable<Webflux>

    fun save(sample: Webflux): Webflux

    fun findOne(id: Long?): Webflux

    fun delete(id: Long?)

}
