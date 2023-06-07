package com.kyk.book.springboot.web;

import com.kyk.book.springboot.domain.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class) // 테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킨다. 여기서는 SpringRunner(스프링부트 테스트와 JUnit 사이에 연결자 역할)를 실행
@WebMvcTest(controllers = HelloController.class, //@ WebMvcTest는 Web(Sping MVC)에 집중할 수 있는 어노테이션으로 @Controller, @ControllerAdvice 등에 사용할 수 있다.
        excludeFilters = {  // 제외 필터를 등록하면 스캔 대상에서 제외할 수 있다.
                // 여기서는 SecurityConfig.class를 제외대상에 넣었다.
                // 왜냐하면 SecurityConfig.class가 생성할 때 필요한 CustomOAuth2UserService를 읽을 수 없어 에러가 발생하므로 해당 클래스를 제외한 것
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        })
public class HelloControllerTest {

    @Autowired           // 스프링이 관리하는 빈을 주입 받는다.
    private MockMvc mvc; // 웹 API를 테스트할 때 사용한다. 스프링 MVC 테스트의 시작점이다. 이 클래스를 통해 HTTP GET, POST 등에 대한 API 테스트를 할 수 있다.

    @WithMockUser(roles="USER")
    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello")) // MockMvc를 통해 /hello 주소로 HTTP GET 요청을 한다.
                .andExpect(status().isOk()) // mvc.perform의 결과를 검증한다. HTTP 헤더의 Status를 검증한다. 200, 404, 500 등의 상태를 검증하는데 여기서는 Ok이므로 200인지 검증
                .andExpect(content().string(hello)); // mvc.perform의 결과를 검증한다. 응답 본문의 내용을 검증한다. 해당 컨트롤러에서 "hello"를 반환하기에 이 값이 맞는지 검사
    }

    @WithMockUser(roles="USER")
    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                    get("/hello/dto")
                            .param("aaa", name)  // .param은 API 테스트할 때 사용될 요청 파라미터를 설정한다.
                            .param("123", String.valueOf(amount))) // 단, String 값만 허용된다.
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
        // jsonPath는 JSON 응답 값을 필드별로 검증할 수 있는 메소드
        // $를 기준으로 필드명을 명시한다.

        // is 부분의 import를 import static org.hamcrest.Matchers.is;로 해야한다. 자동 import에는 없으니 주의
    }

}