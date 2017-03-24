package org.asion.base.ddd.domain;

import org.asion.base.ddd.domain.annotations.DomainEntity;

import java.io.Serializable;

/**
 * base domain model
 * 基础领域模型抽象
 *
 * @param <K> key
 * @author Asion
 * @since 2016/05/02
 */
@DomainEntity
public interface BaseDomainEntity<K extends Serializable> extends BaseModel {
    /**
     * get entity id
     *
     * @return entity id
     */
    K getId();
}