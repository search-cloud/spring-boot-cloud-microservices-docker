//package org.asion.account.test;
//
//import org.asion.account.user;
//import org.asion.security.web.userController;
//import org.hamcrest.core.IsInstanceOf;
//import org.hamcrest.core.StringContains;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Collection;
//
//import static org.junit.Assert.assertEquals;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
///**
// * @author Asion.
// * @since 16/7/2.
// */
//@RunWith(SpringRunner.class)
//@WebMvcTest(userController.class)
//public class userControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void testList() throws Exception {
//        mockMvc.perform(get("/user/list").accept(MediaType.TEXT_PLAIN))
//                .andExpect(status().isOk())
//                .andExpect(view().name("user/list"))
//                .andExpect(model().size(1))
//                .andExpect(model().attributeExists("users"))
//                .andExpect(model().attribute("users", new IsInstanceOf(Collection.class)))
//                .andExpect(content().string(new StringContains("<h1>users : View all</h1>")));
//    }
//
//    @Test
//    public void testOne() throws Exception {
//        mockMvc.perform(get("/user/1")
//                .accept(MediaType.TEXT_PLAIN))
//                .andExpect(status().isOk())
//                .andExpect(view().name("user/view"))
//                .andExpect(model().size(1))
//                .andExpect(model().attributeExists("user"))
//                .andExpect(content().string(new StringContains("<title>users : View</title>")));
//    }
//
//    @Test
//    public void testCreateForm() throws Exception {
//
//        // MockMvc 不支持此种类型的参数: /user/?form
//        mockMvc.perform(get("/user/?form=form")
//                .accept(MediaType.TEXT_PLAIN))
//                .andExpect(status().isOk())
//                .andExpect(view().name("user/form"))
//                .andExpect(model().size(1))
//                .andExpect(content().string(new StringContains("<title>users : Create</title>")));
//    }
//
//    @Test
//    public void testCreate() throws Exception {
//        user user = new user();
//        user.setSummary("Summary Test");
//        user.setText("This is test text!");
//        mockMvc.perform(post("/user/create", user)
//                .accept(MediaType.TEXT_PLAIN))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/user/{user.id}"))
//                .andExpect(model().size(1))
//                .andExpect(model().attributeExists("user.id"));
//    }
//
//    @Test
//    public void testDelete() throws Exception {
//        mockMvc.perform(get("/user/delete/1")
//                .accept(MediaType.TEXT_PLAIN))
//                .andExpect(status().isOk())
//                .andExpect(view().name("user/list"))
//                .andExpect(model().size(1))
//                .andExpect(model().attributeExists("users"))
//                .andExpect(model().attribute("users", new IsInstanceOf(Collection.class)))
//                .andExpect(content().string(new StringContains("<h1>users : View all</h1>")));
//    }
//
//    @Test
//    public void testModify() throws Exception {
//        mockMvc.perform(get("/user/modify/2")
//                .accept(MediaType.TEXT_PLAIN))
//                .andExpect(status().isOk())
//                .andExpect(view().name("user/form"))
//                .andExpect(model().size(1))
//                .andExpect(model().attributeExists("user"))
//                .andExpect(content().string(new StringContains("<h1>users : Create</h1>")));
//    }
//
//    @Test
//    public void testFoo() {
//        //Expected exception in controller
//        try {
//            mockMvc.perform(get("/user/foo"));
//        } catch (Exception e) {
//            assertEquals("Request processing failed; nested exception is java.lang.RuntimeException: Expected exception in controller", e.getMessage());
//        }
//    }
//}
