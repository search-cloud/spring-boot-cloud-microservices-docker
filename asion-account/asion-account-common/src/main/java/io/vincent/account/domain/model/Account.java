package io.vincent.account.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
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
@Data
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
	@Getter
	@AllArgsConstructor
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

}
