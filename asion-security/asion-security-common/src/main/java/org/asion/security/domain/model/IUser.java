package org.asion.security.domain.model;

/**
 * System Abstract User.
 *
 * @author Asion.
 * @since 2017/4/19.
 */
public interface IUser {
    /**
     * Get the user's role.
     *
     * @return the user's role.
     * @see IRole
     */
    IRole getRole();
}
