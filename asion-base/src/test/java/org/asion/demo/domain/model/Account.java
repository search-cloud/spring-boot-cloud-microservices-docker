package org.asion.demo.domain.model;

import lombok.Data;
import org.asion.base.ddd.domain.BaseDomainEntity;
import org.asion.base.ddd.domain.annotations.DomainEntity;

import java.util.Date;

/**
 * @author Asion.
 * @since 2017/3/20.
 */
@Data
@DomainEntity
public class Account implements BaseDomainEntity<Long> {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Integer status;
    private Date createdAt;
}
