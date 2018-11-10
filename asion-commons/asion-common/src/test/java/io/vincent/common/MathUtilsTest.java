package io.vincent.common;

import org.junit.Test;

import static io.vincent.common.utils.MathUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * @author Asion.
 * @since 16/7/7.
 */
public class MathUtilsTest {

    @Test
    public void testMultiply() {
        assertThat(INSTANCE.multiply(2.0, 8.0)).isEqualTo(16.0);
        assertThat(INSTANCE.multiply(null, 8.0)).isEqualTo(0.0);
        assertThat(INSTANCE.multiply(2.0, null)).isEqualTo(0.0);
        assertThat(INSTANCE.multiply(null, null)).isEqualTo(0.0);
    }

    @Test
    public void testDivide() {
        assertThat(INSTANCE.divide(56, 65)).isEqualTo(0.86);
        assertThat(INSTANCE.divide(null, 65)).isEqualTo(0.0);
        assertThat(INSTANCE.divide(56, null)).isEqualTo(0.0);
        assertThat(INSTANCE.divide(null, null)).isEqualTo(0.0);
        assertThat(catchThrowable(() -> INSTANCE.divide(56, 0.0)))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("divisor matches null or zero");

        assertThat(INSTANCE.divide(56, 65, 5)).isEqualTo(0.86154);
    }

    @Test
    public void testMix() {
        assertThat(INSTANCE.mix(3.0, 30.0, -1)).isEqualTo(-27.0);
        assertThat(INSTANCE.mix(null, 30.0, -1)).isEqualTo(-30.0);
        assertThat(INSTANCE.mix(3.0, null, -1)).isEqualTo(3.0);

        assertThat(catchThrowable(() -> INSTANCE.mix(3.0, 30.0, 222)))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("错误的因子！factor只能为1或-1");
    }

    @Test
    public void testAdd() {
        assertThat(INSTANCE.add(298.0, 827.0)).isEqualTo(1125.0);
        assertThat(INSTANCE.add(null, 827.0)).isEqualTo(827.0);
        assertThat(INSTANCE.add(298.0, null)).isEqualTo(298.0);
        assertThat(INSTANCE.add(null, null)).isEqualTo(0.0);
    }

    @Test
    public void testSub() {
        assertThat(INSTANCE.sub(878.0, 564.0)).isEqualTo(314.0);
        assertThat(INSTANCE.sub(null, 568.0)).isEqualTo(-568.0);
        assertThat(INSTANCE.sub(878.0, null)).isEqualTo(878.0);
        assertThat(INSTANCE.sub(null, null)).isEqualTo(0.0);
    }

    @Test
    public void testReverse() {
        assertThat(INSTANCE.reverse(9.0)).isEqualTo(-9.0);
        assertThat(INSTANCE.reverse(null)).isEqualTo(0.0);
    }

    @Test
    public void testAbs() {
        assertThat(INSTANCE.abs(9.0)).isEqualTo(9.0);
        assertThat(INSTANCE.abs(-9.0)).isEqualTo(9.0);
        assertThat(INSTANCE.abs(null)).isEqualTo(0.0);
    }

    @Test
    public void testMatches() {
        assertThat(INSTANCE.matches("1629.0", INTEGER_REG)).isTrue();
        assertThat(INSTANCE.matches("1629", INTEGER_REG)).isTrue();
        assertThat(INSTANCE.matches("1629.12", INTEGER_REG)).isFalse();
        assertThat(INSTANCE.matches(null, INTEGER_REG)).isFalse();
    }

    @Test
    public void testIsInteger() {
        assertThat(INSTANCE.isInteger("1629.0")).isTrue();
        assertThat(INSTANCE.isInteger("1629")).isTrue();
        assertThat(INSTANCE.isInteger("1629.12")).isFalse();
        assertThat(INSTANCE.isInteger(null)).isFalse();
    }

    @Test
    public void testIsDouble() {
        assertThat(INSTANCE.isDouble("0.0")).isTrue();
        assertThat(INSTANCE.isDouble("1629.0")).isTrue();
        assertThat(INSTANCE.isDouble("1629")).isTrue();
        assertThat(INSTANCE.isDouble("1629.12")).isTrue();
        assertThat(INSTANCE.isDouble("isNotANumber")).isFalse();
        assertThat(INSTANCE.isDouble("12123..923")).isFalse();
        assertThat(INSTANCE.isDouble("12123..aa")).isFalse();
        assertThat(INSTANCE.isDouble("bb..14242")).isFalse();
        assertThat(INSTANCE.isDouble(null)).isFalse();
    }
}
