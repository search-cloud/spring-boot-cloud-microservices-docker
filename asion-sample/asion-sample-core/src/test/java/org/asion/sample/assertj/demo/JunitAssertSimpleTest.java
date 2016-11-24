package org.asion.sample.assertj.demo;

import org.asion.sample.common.DateUtil;
import org.assertj.core.data.Offset;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.internal.matchers.Equals;

import java.time.LocalDateTime;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

/**
 * @author Asion.
 * @since 16/7/2.
 */
public class JunitAssertSimpleTest extends SimpleBaseTest {

    @Test
    public void testObjectAssertions() {
        // 1 assert Object
        assertNotNull(demo);
        Assert.assertThat(demo.getCode(), new Equals(demo));
        assertThat(demo).isNotNull()
                .hasFieldOrProperty("code")
                .hasFieldOrProperty("name")
                .hasFieldOrPropertyWithValue("code", "demo Code Test")
                .hasFieldOrPropertyWithValue("name", "demoNameTest");

    }

    @Test
    public void testStringAssertions() {
        // 2 assert String
        assertThat(demo.getCode())
                .isEqualTo("demo Code Test")
                .isEqualToIgnoringCase("demo code test")
                // 忽略字符串前后空格
                .isEqualToIgnoringWhitespace("                demo Code Test ");

    }

    @Test
    public void testNumberAssertions() {
        // 3 assert Number
        assertThat(demo.getAge())
                .isEqualTo(90)
                .isGreaterThan(89)
                .isGreaterThanOrEqualTo(90);

        assertThat(demo.getMoney())
                .isEqualTo(200.001)
                .isBetween(30.0, 300.0)
                .isCloseTo(200.00, Offset.offset(2.0));

    }

    @Test
    public void testDateAssertions() {
        // 4 assert Date
        assertThat(demo.getCreatedAt()).isEqualTo(this.date);

        LocalDateTime localDateTime = DateUtil.dateToLocalDateTime(date).plusDays(1);
        Date date = DateUtil.localDateTimeToDate(localDateTime);

        assertThat(demo.getCreatedAt()).isBefore(date);

        localDateTime = DateUtil.dateToLocalDateTime(this.date).plusDays(-1);
        date = DateUtil.localDateTimeToDate(localDateTime);

        assertThat(demo.getCreatedAt()).isAfter(date);
    }

    @Test
    public void testListAssertions() {
        // 6 assert List
        assertThat(demoList).isNotEmpty().hasSize(10);
        demoSubList.forEach(demo1 -> assertThat(demoList).contains(demo1));
    }

    @Test
    public void testMapAssertions() {
        // 7 assert Map
        assertThat(demoMap).hasSize(10)
                .containsKey(1L)
                .containsValues(demoSubList.get(1));
    }

    @Test
    public void testDigits() {
        assertThat("10").containsOnlyDigits();
    }
}
