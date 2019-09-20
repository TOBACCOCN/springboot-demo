package com.springboot.example.web.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

/**
 * http 请求控制器单元测试
 *
 * @author zhangyonghong
 * @date 2019.9.20
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class HelloControllerTests {

    // private static Logger logger = LoggerFactory.getLogger(HelloControllerTests.class);

    @Autowired
    private HelloController helloController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(helloController).build();
    }

    @Test
    public void helloGET() throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        List<String> list = new ArrayList<>();
        list.add("bar");
        map.put("foo", list);
        list.clear();
        list.add("barz");
        map.put("bar", list);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/hello")
                .accept(MediaType.APPLICATION_JSON)
                .params(map))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void helloPOST() throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        List<String> fooList = new ArrayList<>();
        fooList.add("bar");
        map.put("foo", fooList);
        List<String> barList = new ArrayList<>();
        barList.add("barz");
        map.put("bar", barList);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/hello")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(map))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void helloPOSTUser() throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        List<String> nameList = new ArrayList<>();
        nameList.add("zhangsan");
        map.put("name", nameList);
        List<String> ageList = new ArrayList<>();
        ageList.add("17");
        map.put("age", ageList);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/helloUSER")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(map))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void helloJSON() throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        List<String> fooList = new ArrayList<>();
        fooList.add("bar");
        map.put("foo", fooList);
        List<String> barList = new ArrayList<>();
        barList.add("barz");
        map.put("bar", barList);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/helloJSON")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(map)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        log.info(mvcResult.getResponse().getContentAsString());
    }

}
