package com.kyk.book.springboot.web.dto;

import com.kyk.book.springboot.domain.posts.Posts;
import lombok.Getter;

/**
 * ResponseDto는 엔티티의 필드 중 일부만 사용하므로
 * 생성자로 엔티티를 받아 필드에 값을 넣는다.
 * 즉, 모든 필드를 가진 생성자가 필요하지 않으므로 Dto는 엔티티를 받아 처리한다.
 */
@Getter
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;


    // 매개 변수를 엔티티로 받아와서 받아온 필드의 값으로 적용한다.
    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
