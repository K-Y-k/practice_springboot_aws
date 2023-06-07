package com.kyk.book.springboot.config;

import com.kyk.book.springboot.domain.auth.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * LoginUserArgumentResolver가 스프링에서 인식될 수 있도록 WebMvcConfigurer를 추가한 것이다.
 *
 * HandlerMethodArgumentResolver는 항상 WebMvcConfigurer의 addArgumentReslovers()를 통해 추가해야 한다.
 *
 */
@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoginUserArgumentResolver loginUserArgumentResolver; // 추가하려는 HandlerMethodArgumentResolver


    // HandlerMethodArgumentResolver는 항상 WebMvcConfigurer의 addArgumentReslovers()를 통해 추가해야 한다.
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserArgumentResolver);
    }
}
