package org.asion.base.ddd.domain;

import java.io.Serializable;

/**
 * 基础的领域聚合根
 * @param <K> key
 */
public abstract class BaseDomainAggregate<K extends Serializable> implements BaseModel {
    /**
     * get aggregate root
     *
     * @return root object
     */
    public abstract K getRoot();

    /**
     * get entity id
     *
     * @return entity id
     */
    public K getId() {
        return getRoot();
    }
}