package org.asion.sample.assertj.demo;

import org.asion.sample.common.Demo;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Asion.
 * @since 16/10/9.
 */
abstract class SimpleBaseTest {

    Demo demo = null;
    List<Demo> demoList = null;
    List<Demo> demoSubList = null;
    Map<Long, Demo> demoMap = null;
    Date date = new Date();

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
}
