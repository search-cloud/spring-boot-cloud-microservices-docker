package io.vincent.common.utils

import java.math.BigInteger
import java.nio.ByteBuffer
import java.util.*

/**
 * 简单的UUID
 *
 * @author Asion.
 * @since 16/7/28.
 */
object IdentityUtils {

    /**
     * Generate 13 Digits unique ID from UUID in positive space
     * @return 13 Digits long value representing UUID
     */
    fun get13DigitsUId(): Long {
        val identity = generateLongUUID()
        var s = identity.toString()
        s = s.substring(0, 13)
        return s.toLong()
    }

    /**
     * Generate unique ID from UUID in positive space
     *
     * @return long value representing UUID
     */
    fun generateLongUUID(): Long {
        var value: Long
        do {
            val uid = UUID.randomUUID()
            val buffer = ByteBuffer.wrap(ByteArray(16))
            buffer.putLong(uid.leastSignificantBits)
            buffer.putLong(uid.mostSignificantBits)
            val bi = BigInteger(buffer.array())
            value = bi.toLong()
        } while (value < 0)
        return value
    }
}
