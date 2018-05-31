package org.asion.sample

/**
 * @author Asion
 * @since 16/4/30.
 */
interface SampleRepository {

    fun findAll(): Iterable<Sample>

    fun save(sample: Sample): Sample

    fun findOne(id: Long?): Sample

    fun delete(id: Long?)

}
