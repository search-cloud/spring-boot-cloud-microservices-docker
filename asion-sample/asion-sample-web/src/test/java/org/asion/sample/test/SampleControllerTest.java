package org.asion.sample.test;

import org.asion.sample.Sample;
import org.asion.sample.SampleManager;
import org.asion.sample.mvc.SampleController;
import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.core.StringContains;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Asion.
 * @since 16/7/2.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AsionSampleWebTestsConfiguration.class)
@WebMvcTest(SampleController.class)
public class SampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SampleManager sampleManager;

    @Before
    public void before() {
        when(sampleManager.findAll()).thenReturn(new ArrayList<>());
        when(sampleManager.findOne(1L)).thenReturn(new Sample());
        Sample sample = new Sample();
        sample.setId(2L);
        sample.setSummary("test");
        sample.setText("test text");
        when(sampleManager.save(sample)).thenReturn(sample);
    }

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
    @Ignore
    public void testCreate() throws Exception {
        Sample sample = new Sample();
        sample.setSummary("Summary Test");
        sample.setText("This is test text!");

        Sample sample1 = new Sample();
        sample1.setSummary("Summary Test");
        sample1.setText("This is test text!");
        sample1.setId(1L);

        // 模拟
        when(sampleManager.save(sample)).thenReturn(sample1);

        when(sampleManager.save(new Sample())).thenReturn(null);

        given(sampleManager.save(sample)).willReturn(null);

        mockMvc.perform(post("/sample/create", sample)
                .accept(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/sample/{sample.id}"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("sample.id"));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/sample/delete/{id}", 1)
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(view().name("sample/list"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("samples"))
                .andExpect(model().attribute("samples", new IsInstanceOf(Collection.class)))
                .andExpect(content().string(new StringContains("<h1>Samples : View all</h1>")));
    }

    @Test
    public void testModifyForm() throws Exception {
        Sample sample = new Sample();
        sample.setSummary("Summary Test");
        sample.setText("This is test text!");
        sample.setId(2L);
        when(sampleManager.findOne(2L)).thenReturn(sample);

        mockMvc.perform(get("/sample/modify/{id}", 2)
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
//        try {
//            mockMvc.perform(get("/sample/foo"));
//        } catch (Exception e) {
//            assertEquals("Request processing failed; nested exception is java.lang.RuntimeException: Expected exception in controller", e.getMessage());
//        }
        // ava 8 exception assertion, standard style ...
        assertThatThrownBy(() -> mockMvc.perform(get("/sample/foo")))
                .hasCauseInstanceOf(RuntimeException.class)
                .hasMessage("Request processing failed; nested exception is java.lang.RuntimeException: Expected exception in controller");

        // ... or BDD style
        Throwable thrown = catchThrowable(() -> mockMvc.perform(get("/sample/foo")));
        assertThat(thrown).hasMessageContaining("Expected exception in controller");

        assertThat(33).as("check %s's age", "name").isEqualTo(33);
    }
}
