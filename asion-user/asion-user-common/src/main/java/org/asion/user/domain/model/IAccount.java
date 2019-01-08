package org.asion.user.domain.model;

/**
 * @author Asion.
 * @since 2017/4/19.
 */
public interface IAccount {

	/**
	 * 获取账号名称
	 *
	 * @return 账号名称
	 */
	String getUsername();

	/**
	 * 获取密码
	 *
	 * @return 密码
	 */
	String getPassword();

	boolean isEnabled();
}
