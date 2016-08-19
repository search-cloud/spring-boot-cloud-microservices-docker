package org.asion.sample.assertj.demo;

import org.assertj.core.data.Offset;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Asion.
 * @since 16/7/2.
 */
public class AssertJTest {

    private Demo demo = null;
    private List<Demo> demoList = null;
    private List<Demo> demoSubList = null;
    private Map<Long, Demo> demoMap = null;
    private Date date = new Date();

    @Before
    public void before() {
        // init single
        demo = new Demo(999L, "demo Code Test", "demoNameTest", 90, 200.001, 0, date);
        // init list
        demoList = new ArrayList<>(10);
        demoSubList = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            Demo demo = new Demo((long) i, "demoCode"+i, "demoName"+i, (20 + i), (200.0 * i), i, date);
            demoList.add(demo);
            if (i%2 == 0) {
                demoSubList.add(demo);
            }
        }
        // init map
        demoMap = demoList.stream().collect(Collectors.toMap(Demo::getId, demo1 -> demo1));
    }

    @Test
    public void testObjectAssertions() {
        // 1 assert Object
        assertThat(demo).isNotNull()
                .hasFieldOrProperty("code").hasFieldOrProperty("name")
                .hasFieldOrPropertyWithValue("code", "demo Code Test").hasFieldOrPropertyWithValue("name", "demoNameTest");

    }

    @Test
    public void testStringAssertions() {
        // 2 assert String
        assertThat(demo.getCode())
                .isEqualTo("demo Code Test")
                .isEqualToIgnoringCase("demo code test")
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
        assertThat(demoList).hasSize(10);
        demoSubList.stream().forEach(demo1 -> assertThat(demoList).contains(demo1));
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
