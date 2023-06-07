package com.kyk.book.springboot.domain.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 반복되는 SessionUser user = (SessionUser) httpSession.getAttribute("user"); 코드를
 * 메소드 인자로 세션 값을 바로 받을 수 있도록 변경하기 위한 전용 어노테이션
 */
@Target(ElementType.PARAMETER) // 현재 클래스 어노테이션이 생성될 수 있는 위치를 지정한다. PARAMETER로 지정했으니 메소드의 파라미터로 선언된 객체에서만 사용할 수 있다.
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser { // @interface는 어노테이션 클뢔스로 지정한다는 뜻
}
