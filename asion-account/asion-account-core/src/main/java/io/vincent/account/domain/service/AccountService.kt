package io.vincent.account.domain.service

/**
 * Created by Vincent on 1/7/19.
 * @author Vincent
 * @since 1.0, 1/7/19
 */
interface AccountService {
    fun disable(id: Long): Long?
}