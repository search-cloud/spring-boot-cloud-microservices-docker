package org.asion.sample.assertj.demo;

import org.asion.sample.common.DateUtil;
import org.asion.sample.common.Demo;
import org.hamcrest.core.IsCollectionContaining;
import org.hamcrest.number.IsCloseTo;
import org.junit.Test;
import org.mockito.internal.matchers.Equals;
import org.mockito.internal.matchers.GreaterOrEqual;
import org.mockito.internal.matchers.GreaterThan;
import org.mockito.internal.matchers.LessThan;

import java.time.LocalDateTime;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;


/**
 * @author Asion.
 * @since 16/7/2.
 */
public class JunitAssertSimpleTest extends SimpleBaseTest {

    /**
     * 1 assert Object
     */
    @Test
    public void testObjectAssertions() {
        // 1 assert Object
        assertNotNull(demo); // 断言不为null

        // 断言对象里面字段的值
        assertEquals(demo.getCode(), "demo Code Test");
        assertThat(demo.getCode(), new Equals("demo Code Test"));
        // 断言里面有没有该字段？
        // reflection
//                .hasFieldOrProperty("code")
//                .hasFieldOrProperty("name")

    }

    /**
     * 2 assert String
     */
    @Test
    public void testStringAssertions() {
        // 2 assert String
        assertEquals(demo.getCode(), "demo Code Test");
        assertThat(demo.getCode(), new Equals("demo Code Test"));
        // 断言忽略大小写？
        // 忽略字符串前后空？

    }

    /**
     * 3 assert Number
     */
    @Test
    public void testNumberAssertions() {
        // 3 assert Number
        // 断言相等
        assertEquals(demo.getAge(), 90);
        assertThat(demo.getAge(), new Equals(90));
        // 断言大于
        assertTrue(demo.getAge() > 89);
        assertThat(demo.getAge(), new GreaterThan<>(89));
        // 断言大于或等于
        assertTrue(demo.getAge() > 90 || demo.getAge() == 90);
        assertThat(demo.getAge(), new GreaterOrEqual<>(90));


        // 断言相等
        assertEquals(demo.getMoney(), 200.001, 0.001);
        assertThat(demo.getMoney(), new Equals(200.001));
        // 断言大于
        assertTrue(demo.getMoney() > 200.000);
        // 断言大于或等于
        assertTrue(demo.getMoney() > 200.001 || demo.getMoney() == 200.001);

        // 接近于
        assertThat(demo.getMoney(), new IsCloseTo(200.001, 0.001));

    }

    /**
     * 4 assert Date
     */
    @Test
    public void testDateAssertions() {
        // 4 assert Date
        assertThat(demo.getCreatedAt(), new Equals(this.date));

        LocalDateTime localDateTime = DateUtil.dateToLocalDateTime(date).plusDays(1);
        Date date = DateUtil.localDateTimeToDate(localDateTime);

        assertThat(demo.getCreatedAt(), new LessThan<>(date));

        localDateTime = DateUtil.dateToLocalDateTime(this.date).plusDays(-1);
        date = DateUtil.localDateTimeToDate(localDateTime);

        assertThat(demo.getCreatedAt(), new GreaterThan<>(date));
    }

    /**
     * 6 assert List
     */
    @Test
    public void testListAssertions() {
        // 6 assert List
        assertNotNull(demoList);
        assertThat(demoList.size(), new Equals(10));

        for (Demo demo1 : demoList) {
            assertThat(demoList, IsCollectionContaining.hasItems(demo1));
        }
    }

    /**
     * 7 assert Map
     */
    @Test
    public void testMapAssertions() {
        // 7 assert Map
        assertNotNull(demoMap);
        assertThat(demoMap.size(), new Equals(10));

        // containsKey containsValues
        assertTrue(demoMap.containsKey(1L));
        assertTrue(demoMap.containsValue(demoSubList.get(1)));

    }

    @Test
    public void testDigits() {
        // 判断字符串是否可以转换成数字
//        assertThat("10");
    }
}
