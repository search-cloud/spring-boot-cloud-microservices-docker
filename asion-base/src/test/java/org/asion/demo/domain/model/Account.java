package org.asion.demo.domain.model;

import org.asion.base.ddd.domain.BaseDomainEntity;
import org.asion.base.ddd.domain.annotations.DomainEntity;

import java.util.Date;

/**
 * @author Asion.
 * @since 2017/3/20.
 */
@DomainEntity
public class Account implements BaseDomainEntity<Long> {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Integer status;
    private Date createdAt;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
