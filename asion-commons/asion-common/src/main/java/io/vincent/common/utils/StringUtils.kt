package io.vincent.common.utils

/**
 * Created by Vincent on 11/2/18.
 * @author Vincent
 * @since 1.0, 11/2/18
 */
object StringUtils {

    fun isNotBlank(value: String?): Boolean {
        return value != null && !value.isEmpty()
    }

    fun isBlank(value: String?): Boolean {
        return value == null || value.isEmpty()
    }
}