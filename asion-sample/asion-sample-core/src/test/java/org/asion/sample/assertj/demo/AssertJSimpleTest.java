package org.asion.sample.assertj.demo;

import org.asion.sample.common.DateUtil;
import org.assertj.core.data.Offset;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Asion.
 * @since 16/7/2.
 */
public class AssertJSimpleTest extends SimpleBaseTest {

    /**
     * 1 assert Object
     */
    @Test
    public void testObjectAssertions() {
        // 1 assert Object
        assertThat(demo).isNotNull()
                // 断言里面有没有该字段
                .hasFieldOrProperty("code")
                .hasFieldOrProperty("name")
                // 断言对象里面字段与值
                .hasFieldOrPropertyWithValue("code", "demo Code Test")
                .hasFieldOrPropertyWithValue("name", "demoNameTest");

    }

    /**
     * 2 assert String
     */
    @Test
    public void testStringAssertions() {
        // 2 assert String
        assertThat("")
                .isEqualTo("demo Code Test")
                // 断言忽略大小写
                .isEqualToIgnoringCase("demo code test")
                // 忽略字符串前后空格
                .isEqualToIgnoringWhitespace("                demo Code Test ");

    }

    /**
     * 3 assert Number
     */
    @Test
    public void testNumberAssertions() {
        // 3 assert Number
        assertThat(demo.getAge())
                // 断言相等
                .isEqualTo(90)
                // 断言大于
                .isGreaterThan(89)
                // 断言大于或等于
                .isGreaterThanOrEqualTo(90);

        assertThat(demo.getMoney())
                .isEqualTo(200.001)
                .isBetween(30.0, 300.0)
                .isCloseTo(200.00, Offset.offset(2.0));

    }

    /**
     * 4 assert Date
     */
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

    /**
     * 6 assert List
     */
    @Test
    public void testListAssertions() {
        // 6 assert List
        assertThat(demoList).isNotNull().isNotEmpty().hasSize(10);
        demoSubList.forEach(demo1 -> assertThat(demoList).contains(demo1));
    }

    /**
     * 7 assert Map
     */
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

    /**
     * The following example shows an incorrect AssertJ API usage:
     */
    @Test
    public void testErrorUsage() {
        // The following example shows an incorrect AssertJ API usage:

        String a1 = "";
        String a2 = "";
        String a3 = "";
        String b1 = "";
        String b2 = "";
        String b3 = "";

        // BAD USAGE : DON'T DO THIS ! It does not assert anything
        assertThat(a1.equals(b1));
        assertThat(a2.equals(b2));
        assertThat(a3.equals(b3));

        // DO THIS :
        assertThat(a1).isEqualTo(b1);
        assertThat(a2).isEqualTo(b2);
        assertThat(a3).isEqualTo(b3);

        // OR THIS (less classy but ok):
        assertThat(a1.equals(b1)).isTrue();
        assertThat(a2.equals(b2)).isTrue();
        assertThat(a3.equals(b3)).isTrue();
    }
}
