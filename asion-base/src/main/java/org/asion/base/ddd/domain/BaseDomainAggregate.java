package org.asion.base.ddd.domain;

import org.asion.base.ddd.domain.annotations.DomainAggregate;

import java.io.Serializable;

/**
 * base domain aggregate
 * 基础的领域聚合根
 *
 * @param <K> key
 * @author Asion
 * @since 2016/05/02
 */
@DomainAggregate
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