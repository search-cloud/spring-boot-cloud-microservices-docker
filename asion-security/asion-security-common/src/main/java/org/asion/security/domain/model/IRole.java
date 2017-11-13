package org.asion.security.domain.model;

/**
 * The Role of system user.
 *
 * @author Asion.
 * @since 2017/4/19.
 */
public interface IRole {

    /**
     * Get the resource of the role.
     *
     * @return the role resource.
     * @see IResource
     */
    IResource getResource();

    /**
     * Get the resource's permission of the role.
     *
     * @return the resource's permission of the role.
     * @see IPermission
     */
    IPermission getPermission();

}
