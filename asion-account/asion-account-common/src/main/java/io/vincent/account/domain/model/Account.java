package io.vincent.account.domain.model;

import org.asion.base.ddd.domain.BaseDomainEntity;
import org.asion.base.ddd.domain.annotations.DomainEntity;
import org.asion.user.domain.model.IAccount;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * 账号与授权信息
 *
 * @author Asion.
 * @since 16/4/29.
 */
@DomainEntity
public class Account implements BaseDomainEntity<Long>, IAccount, Serializable {

	private Long id;

	/**
	 * 逻辑唯一code
	 */
	private String code;

	/**
	 * 登录名称
	 */
	private String username;

	/**
	 * 显示昵称
	 */
	private String nickName;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 曾用密码
	 */
	private String usedPassword;

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 微信用户的unionId
	 */
	private String wechatId;

	/**
	 * 账号是否可用（是否有效）
	 */
	private boolean enabled;

	/**
	 * 父账号的ID，父子账号管理时使用
	 */
	private Long parentId;

	/**
	 * 账号状态
	 */
	private int status;

	/**
	 * 最后一次登录时间
	 */
	private Date lastLoginAt;

	/**
	 * 操作错误次数，如：登录，验证码错误等
	 */
	private int operationErrorCount;

	/**
	 * 账号创建时间
	 */
	private Date createdAt;

	/**
	 * 账号更新时间
	 */
	private Date updatedAt;

	/**
	 * 账号的状态
	 */
	public enum Status {
		/**
		 * 正常
		 */
		NORMAL("正常", 0),

		/**
		 * 注册（未验证邮箱）
		 */
		REGISTER("注册", 1),

		/**
		 * 禁用（系统禁用）
		 */
		DISABLE("禁用", 2),

		/**
		 * 异常（多次登录失败等）
		 */
		ABNORMAL("异常", 3),

		/**
		 * 废弃的（如：seller角色的account，却根据id找不到seller）
		 */
		DISUSED("废弃", -11),

		/**
		 * 子账号被商家停用
		 */
		STOPPED("停用", -1);


		private String name;
		private int code;

		Status(String name, int code) {
			this.name = name;
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public int getCode() {
			return code;
		}

		/**
		 * 根据状态码获取状态名称
		 *
		 * @param code 状态码
		 * @return 状态名称
		 */
		public static String getName(int code) {
			return Arrays.stream(Status.values()).filter(c -> c.getCode() == code).findFirst().map(c -> c.name).orElse(null);
		}
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsedPassword() {
		return usedPassword;
	}

	public void setUsedPassword(String usedPassword) {
		this.usedPassword = usedPassword;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getLastLoginAt() {
		return lastLoginAt;
	}

	public void setLastLoginAt(Date lastLoginAt) {
		this.lastLoginAt = lastLoginAt;
	}

	public int getOperationErrorCount() {
		return operationErrorCount;
	}

	public void setOperationErrorCount(int operationErrorCount) {
		this.operationErrorCount = operationErrorCount;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
