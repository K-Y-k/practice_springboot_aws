package com.kyk.book.springboot.domain.posts;


import org.junit.After;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest // 이 어노테이션 덕분에 H2 데이터베이스를 따로 실행 없이도 자동으로 실행해준다.
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After // JUnit에서 단위 테스트가 끝날 때마다 수행되는 메소드를 지정한다.
           // 보통 배포전 전체 테스트를 수행할 때 테스트 간 데이터 침범을 막기 위해 사용한다. 여러 테스트가 동시에 수행되면 변경된 데이터가 그대로 남아있기 때문
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        Posts post = Posts.builder()      // 빌더 패턴을 활용한 해당 값을 생성
                .title(title)
                .content(content)
                .author("kyk@naver.com")
                .build();

        postsRepository.save(post);       // 테이블 posts에 해당 id 값이 있다면 update, 없다면 insert 쿼리가 실행된다.

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }




}