package com.kyk.book.springboot.web;

import com.kyk.book.springboot.service.PostsService;
import com.kyk.book.springboot.web.dto.PostsResponseDto;
import com.kyk.book.springboot.web.dto.PostsSaveRequestDto;
import com.kyk.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor  // 생성자로 빈 객체를 받도록 하는 방식인데, 이 어노테이션 덕분에 final로 선언된 모든 필드를 인자값으로 하는 생성자를 대신 생성해준다.
                          // 이 어노테이션 덕분에 생성자를 직접 안 써도 된다. 이는 해당 클래스의 의존성 관계가 변경될 때마다 생성자 코드를 계속해서 수정하는 번거로움을 해결한다.
@RestController
public class PostsApiController {
    private final PostsService postsService; // @RequiredArgsConstructor가 대신 생성자를 생성해줌


    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }


    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }


    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }


    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }

}
