package org.asion.bot.test.spec;

import com.ytx.seeker.selenium.spec.TmallPageProcessSpecification;
import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import us.codecraft.webmagic.selector.Html;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Asion.
 * @since 2018/5/15.
 */
public class TmallPageProcessSpecificationTest {

    private static String html = "";

    @BeforeClass
    public void before() throws IOException {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("test-list.html");
        List<String> strings = IOUtils.readLines(inputStream, "GBK");
        html = String.join("", strings);
    }

    @Test
    public void shouldProcess() {
    }

    @Test
    public void isListPage() {
    }

    @Test
    public void isDetailPage() {
    }

    @Test
    public void isChinaPage() {
    }

    @Test
    public void getCurrentPageNo() throws IOException {
        String currentPageNo = TmallPageProcessSpecification.INSTANCE.getCurrentPageNo(Html.create(html));
        System.out.println(currentPageNo);
    }
}