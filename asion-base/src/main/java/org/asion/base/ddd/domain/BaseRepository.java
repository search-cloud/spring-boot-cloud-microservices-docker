package org.asion.base.ddd.domain;

import org.asion.base.ddd.domain.annotations.DomainRepository;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Optional;

/**
 * BaseRepository C R U D.
 *
 * @author Asion.
 * @since 2017/3/20.
 */
@DomainRepository
public interface BaseRepository<T extends BaseDomainEntity, ID extends Serializable> {


    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity must not be {@literal null}.
     * @return the saved entity will never be {@literal null}.
     */
    @NotNull
    <S extends T> S create(S entity);

    /**
     * Update entity
     *
     * @param entity must not be {@literal null}.
     * @return the updated entity will never be {@literal null}.
     */
    @NotNull
    <S extends T> S update(S entity);

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    Optional<T> findById(ID id);

    /**
     * find one
     *
     * @param id id
     * @return entity
     */
    T findOne(ID id);

}
