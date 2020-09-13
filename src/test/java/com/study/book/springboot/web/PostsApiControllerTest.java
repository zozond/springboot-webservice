package com.study.book.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.book.springboot.domain.posts.Posts;
import com.study.book.springboot.domain.posts.PostsRepository;
import com.study.book.springboot.web.dto.PostsSaveRequestDto;
import com.study.book.springboot.web.dto.PostsUpdateRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void registerTest() throws Exception {
        String title = "title";
        String content = "content";
        String author = "author";

        PostsSaveRequestDto dto = PostsSaveRequestDto.builder().title(title).content(content).author(author).build();
        String url = "http://localhost:" + port + "/api/v1/posts";

//        ResponseEntity responseEntity = restTemplate.postForEntity(url, dto, Long.class);
//        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        Assertions.assertThat((Long) responseEntity.getBody()).isGreaterThan(0L);

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk());

        List<Posts> list = postsRepository.findAll();

        Assertions.assertThat(list.get(0).getTitle()).isEqualTo(title);
        Assertions.assertThat(list.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void updateTest() throws Exception {

        String title = "title";
        String content = "content";
        String author = "author";
        Posts savePosts = postsRepository.save(Posts.builder().title(title).content(content).author(author).build());
        Long updateId = savePosts.getId();

        String expectedTitle = "title";
        String expectedContent = "content";

        PostsUpdateRequestDto updateRequestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle).content(expectedContent).build();
        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        mockMvc.perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateRequestDto)))
                .andExpect(status().isOk());

        List<Posts> list = postsRepository.findAll();
        Assertions.assertThat(list.get(0).getTitle()).isEqualTo(expectedTitle);
        Assertions.assertThat(list.get(0).getContent()).isEqualTo(expectedContent);
    }
}

