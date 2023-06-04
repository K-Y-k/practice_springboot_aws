package com.kyk.book.springboot.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // JSON으로 반환하는 컨트롤러로 만들어 준다. 예전에는 @ResponseBody를 각 메소드마다 선언했던 것을 편하게 만들어준 것
public class HelloController {
    @GetMapping("/hello") // HTTP 메소드인 Get의 요청을 받을 수 있는 API를 만들어 준다.
                          // 예전에는 @RequestMapping(method=RequestMethod.GET)으로 사용
    public String hello() {
        return "hello";
    }
}
