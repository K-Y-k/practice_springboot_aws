package com.kyk.book.springboot.web;

import com.kyk.book.springboot.service.PostsService;
import com.kyk.book.springboot.web.dto.PostsListResponseDto;
import com.kyk.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;


    /**
     * 메인 폼
     */
    @GetMapping("/")
    public String index(Model model) {
        List<PostsListResponseDto> allList = postsService.findAllDesc();

        model.addAttribute("posts", allList);
        return "index"; // src/main/resources/templates/index.mustache로 전환되고 View Resolver가 처리한다.
    }


    /**
     * 저장 폼
     */
    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }


    /**
     * 수정 폼
     */
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }


}
