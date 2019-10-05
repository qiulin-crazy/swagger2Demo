package com.qiulin.swagger2;

import com.qiulin.swagger2.controller.UserController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Swagger2ApplicationTests {

    private MockMvc mockMvc;
    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController()).build();
    }

    @Test
   public void testUserController() throws Exception {
        RequestBuilder requestBuilder;
        //1.get 获取user列表
        requestBuilder = get("/users/list");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));

        //2.post 提交一个user
        requestBuilder = post("/users/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"name\":\"张三\",\"age\":20}");
        mockMvc.perform(requestBuilder)
                .andExpect(content().string(equalTo("success")));
// 3、get获取user列表，应该有刚才插入的数据
        requestBuilder = get("/users/list");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[{\"id\":1,\"name\":\"张三\",\"age\":20}]")));

        // 4、put修改id为1的user
        requestBuilder = put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"张三\",\"age\":30}");
        mockMvc.perform(requestBuilder)
                .andExpect(content().string(equalTo("success")));

        // 5、get一个id为1的user
        requestBuilder = get("/users/1");
        mockMvc.perform(requestBuilder)
                .andExpect(content().string(equalTo("{\"id\":1,\"name\":\"张三\",\"age\":30}")));

        // 6、del删除id为1的user
        requestBuilder = delete("/users/1");
        mockMvc.perform(requestBuilder)
                .andExpect(content().string(equalTo("success")));

        // 7、get查一下user列表，应该为空
        requestBuilder = get("/users/list");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));
    }

}
