package org.asion.user.domain.model;

/**
 * System Resource.
 *
 * @author Asion.
 * @since 2017/4/19.
 */
public interface IResource {
    /**
     * Get the permission of the resource.
     *
     * @return the permission of the resource.
     * @see IPermission
     * @see IRole
     */
    IPermission getPermission();
}
