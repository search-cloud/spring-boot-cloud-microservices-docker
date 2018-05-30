package org.asion.bot.domain.model

import java.io.Serializable

/**
 * Comment Base Repository CRUD
 *
 * @author Asion
 * @since 2017-01-20
 */
interface BaseRepository<T, in ID : Serializable> {

    /**
     * 保存, 包括新增与更新
     *
     * @param entity 实体
     * @return 已经保存的实体
     */
    fun <S : T> save(entity: S): S

    /**
     * Saves all given entities.
     *
     * @param entities 实体
     * @return the saved entities
     * @throws IllegalArgumentException in case the given entity is null.
     */
    fun <S : T> save(entities: List<S>): List<S>

    /**
     * 根据主键返回
     *
     * @param id 主键
     * @return 对象
     */
    fun findOne(id: ID): T?

    /**
     * 根据ids返回实体列表
     * Returns the instances of the type with the given IDs.
     *
     * @param ids the given IDs.
     * @return the instances of the given IDs.
     */
    fun findList(ids: List<ID>): List<T>

    /**
     * 根据id返回对象是否存在
     *
     * @param id must not be null.
     * @return true if an entity with the given id exists, false otherwise
     */
    fun exists(id: ID): Boolean

    /**
     * 根据主键删除
     *
     * @param id 主键
     */
    fun delete(id: ID): Boolean

    /**
     * Deletes a given entity.
     *
     * @param entity Need to delete entity.
     * @throws IllegalArgumentException in case the given entity is null.
     */
    fun delete(entity: T): Boolean

}
