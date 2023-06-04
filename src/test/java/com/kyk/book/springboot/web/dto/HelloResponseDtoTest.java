package com.kyk.book.springboot.web.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class HelloResponseDtoTest {

   @Test
   public void 롬복_기능테스트() {
       // given
       String name = "test";
       int amount = 1000;

       // when
       HelloResponseDto dto = new HelloResponseDto(name, amount);

       // then
       assertThat(dto.getName()).isEqualTo(name);     // assertThat은 assertj라는 테스트 검증 라이브러리의 검증 메소드
       assertThat(dto.getAmount()).isEqualTo(amount);
   }


}