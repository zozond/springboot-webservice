package com.study.book.springboot.domain.posts;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsTest {
    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanUp() {
        postsRepository.deleteAll();
    }

    @Test
    public void postsReadTest() {
        String title = "title";
        String content = "content";
        String author = "author";

        Posts posts = Posts.builder().title(title).content(content).author(author).build();
        postsRepository.save(posts);
        List<Posts> postsList = postsRepository.findAll();
        Posts readPost = postsList.get(0);
        Assertions.assertThat(readPost.getTitle()).isEqualTo(posts.getTitle());
        Assertions.assertThat(readPost.getContent()).isEqualTo(posts.getContent());
        Assertions.assertThat(readPost.getAuthor()).isEqualTo(posts.getAuthor());
    }

    @Test
    public void timeTest() {
        String title = "title";
        String content = "content";
        String author = "author";
        LocalDateTime now = LocalDateTime.of(2020,9,5,0,0,0);
        Posts posts = Posts.builder().title(title).content(content).author(author).build();
        postsRepository.save(posts);

        List<Posts> postsList = postsRepository.findAll();
        Posts readPost = postsList.get(0);

        System.out.println(">>> 이시간 이후 : " + now);
        System.out.println(">>> 생성시간 : " + readPost.getCreatedDate());
        System.out.println(">>> 변경시간 : " + readPost.getModifiedDate());

        Assertions.assertThat(readPost.getTitle()).isEqualTo(posts.getTitle());
        Assertions.assertThat(readPost.getContent()).isEqualTo(posts.getContent());
        Assertions.assertThat(readPost.getAuthor()).isEqualTo(posts.getAuthor());
        Assertions.assertThat(readPost.getCreatedDate()).isAfter(now);
        Assertions.assertThat(readPost.getModifiedDate()).isAfter(now);
    }

}

