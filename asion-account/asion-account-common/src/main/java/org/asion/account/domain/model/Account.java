package org.asion.account.domain.model;

import lombok.Data;
import org.asion.base.ddd.domain.BaseDomainEntity;
import org.asion.base.ddd.domain.annotations.DomainEntity;
import org.asion.user.domain.model.IAccount;

import java.security.Principal;
import java.util.Date;

/**
 * 账号与授权信息
 *
 * @author Asion.
 * @since 16/4/29.
 */
@Data
@DomainEntity
public class Account implements BaseDomainEntity<Long>, Principal, IAccount {

    private Long id;

    /**
     *
     */
    private String code;

    /**
     * 登录名称
     */
    private String name;

    /**
     * 显示昵称
     */
    private String nickName;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否可用（是否有效）
     */
    private boolean enabled;

    /**
     * 账号创建时间
     */
    private Date createdAt;

    /**
     * 账号更新时间
     */
    private Date updatedAt;

}
