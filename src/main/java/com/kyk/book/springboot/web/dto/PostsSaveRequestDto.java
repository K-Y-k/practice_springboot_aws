package com.kyk.book.springboot.web.dto;

import com.kyk.book.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 엔티티 클래스를 기준으로 값들이 변경하지 않기 위한 클래스이다.
 * 값들은 자주 변경되는데 엔티티 클래스를 기준으로 하면 영향이 너무 크기 때문에
 * 즉, 꼭 엔티티 클래스와 컨트롤러에서 쓸 Dto는 분리해서 사용해야 한다.
 */

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {

    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
