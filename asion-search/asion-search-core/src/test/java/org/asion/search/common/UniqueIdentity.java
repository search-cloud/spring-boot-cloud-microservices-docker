package org.asion.search.common;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * @author Asion.
 * @since 16/7/28.
 */
public class UniqueIdentity {

    /**
     * Generate unique ID from UUID in positive space
     *
     * @return long value representing UUID
     */
    public static Long generateLongUId() {
        long val;
        do {
            final UUID uid = UUID.randomUUID();
            final ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
            buffer.putLong(uid.getLeastSignificantBits());
            buffer.putLong(uid.getMostSignificantBits());
            final BigInteger bi = new BigInteger(buffer.array());
            val = bi.longValue();
        } while (val < 0);
        return val;
    }

    /**
     * Generate 13 Digits unique ID from UUID in positive space
     * @return 13 Digits long value representing UUID
     */
    public static Long get13DigitsUId() {
        Long uniqueIdentity = generateLongUId();
        String s = String.valueOf(uniqueIdentity);
        s = s.substring(0, 13);
        return Long.valueOf(s);
    }
}
