package com.kyk.book.springboot.web;

import com.kyk.book.springboot.domain.auth.LoginUser;
import com.kyk.book.springboot.domain.auth.dto.SessionUser;
import com.kyk.book.springboot.service.PostsService;
import com.kyk.book.springboot.web.dto.PostsListResponseDto;
import com.kyk.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;


    /**
     * 메인 폼
     */
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        // CustomOAuth2UserService에서 로그인 성공 시 세션에 SessionUser를 저장하도록 구성
        // 즉, 로그인 성공 시 httpSession.getAttribute("user")에서 값을 가져올 수 있다.
        // 아래 코드는 다른 컨트롤러와 메소드에서 세션값이 필요하면 그때마다 직접 세션에서 값을 가져와야하므로 반복되는 코드이다.
        // 그래서 이 코드를 메소드 인자로 세션 값을 바로 받을 수 있도록 변경
//         반복되는 SessionUser user = (SessionUser) httpSession.getAttribute("user");를 -> @LoginUser SessionUser user로 개선

        // 세션에 저장된 값이 있을 때만 model에 userName으로 등록한다.
        // 세션에 저장된 값이 없으면 model에는 아무런 값이 없는 상태라서 index.mustache에서 로그인 버튼이 보이게 된다.
        if (user != null) {
            model.addAttribute("userName", user);
            log.info("유저 이름 = {}", user.getName());
            log.info("유저 이메일 = {}", user.getEmail());
            log.info("유저 사진 = {}", user.getPicture());
        }

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
