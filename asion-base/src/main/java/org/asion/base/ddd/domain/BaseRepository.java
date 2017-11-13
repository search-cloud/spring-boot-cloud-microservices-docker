package org.asion.base.ddd.domain;

import org.asion.base.ddd.domain.annotations.DomainRepository;

import java.io.Serializable;

/**
 * BaseRepository C R U D.
 *
 * @author Asion.
 * @since 2017/3/20.
 */
@DomainRepository
public interface BaseRepository<ID extends Serializable, T extends BaseDomainEntity> {


    /**
     * create entity
     *
     * @param entity entity
     * @return entity
     */
    T create(T entity);

    /**
     * create entity
     *
     * @param entity entity
     * @return entity
     */
    T update(T entity);

    /**
     * find one
     *
     * @param id id
     * @return entity
     */
    T findOne(ID id);

    /**
     * delete
     *
     * @param id id
     */
    void delete(ID id);
}
