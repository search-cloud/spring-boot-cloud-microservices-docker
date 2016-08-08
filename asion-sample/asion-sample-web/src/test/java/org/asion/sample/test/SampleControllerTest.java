package org.asion.sample.test;

import org.asion.sample.Sample;
import org.asion.sample.mvc.SampleController;
import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.core.StringContains;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Asion.
 * @since 16/7/2.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
public class SampleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testList() throws Exception {
        mockMvc.perform(get("/sample/list").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(view().name("sample/list"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("samples"))
                .andExpect(model().attribute("samples", new IsInstanceOf(Collection.class)))
                .andExpect(content().string(new StringContains("<h1>Samples : View all</h1>")));
    }

    @Test
    public void testOne() throws Exception {
        mockMvc.perform(get("/sample/1")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(view().name("sample/view"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("sample"))
                .andExpect(content().string(new StringContains("<title>Samples : View</title>")));
    }

    @Test
    public void testCreateForm() throws Exception {

        // MockMvc 不支持此种类型的参数: /sample/?form
        mockMvc.perform(get("/sample/?form=form")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(view().name("sample/form"))
                .andExpect(model().size(1))
                .andExpect(content().string(new StringContains("<title>Samples : Create</title>")));
    }

    @Test
    public void testCreate() throws Exception {
        Sample sample = new Sample();
        sample.setSummary("Summary Test");
        sample.setText("This is test text!");
        mockMvc.perform(post("/sample/create", sample)
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/sample/{sample.id}"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("sample.id"));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(get("/sample/delete/1")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(view().name("sample/list"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("samples"))
                .andExpect(model().attribute("samples", new IsInstanceOf(Collection.class)))
                .andExpect(content().string(new StringContains("<h1>Samples : View all</h1>")));
    }

    @Test
    public void testModify() throws Exception {
        mockMvc.perform(get("/sample/modify/2")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(view().name("sample/form"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("sample"))
                .andExpect(content().string(new StringContains("<h1>Samples : Create</h1>")));
    }

    @Test
    public void testFoo() {
        //Expected exception in controller
        try {
            mockMvc.perform(get("/sample/foo"));
        } catch (Exception e) {
            assertEquals("Request processing failed; nested exception is java.lang.RuntimeException: Expected exception in controller", e.getMessage());
        }
    }
}
