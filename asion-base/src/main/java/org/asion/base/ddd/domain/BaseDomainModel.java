package org.asion.base.ddd.domain;

import java.io.Serializable;

/**
 * 基础领域模型抽象
 * @param <K> key
 */
public interface BaseDomainModel<K extends Serializable> extends BaseModel {
    /**
     * get entity id
     *
     * @return entity id
     */
    K getId();
}