package com.kyk.book.springboot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


/**
 * 앞으로 만들 프로젝트의 메인 클래스
 */

@EnableJpaAuditing      // JPA Auditing 어노테이션들을 모두 활성화 할 수 있도록 해준다.
@SpringBootApplication  // 이 어노테이션으로 인해 스프링 부트의 자동 설정, 스프링 빈 읽기와 생성을 모두 자동으로 설정된다.
                        // @SpringBootApplication의 위치부터 설정을 읽어가기 때문에 이 어노테이션이 선언된 메인 클래스는 프로젝트의 최상단에 위치해야만 한다.
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); // SpringApplication.run으로 내장 WAS(웹 어플리케이션 서버)를 실행한다.
                                                        // 내장 WAS 덕분에 외부에 서버(Tomcat)을 따로 설치할 필요가 없게 되었고 스프링 부트로 만들어진 Jar 파일(= 실행 가능한 Java 패키징 파일)로 실행하면 된다.
        // 스프링 부트에서 내장 WAS만 사용되는 것은 아니지만 이를 권장하는 이유
        // -> 언제 어디서나 같은 환경에서 스프링 부트를 배포할 수 있다.
        //    : 외장 WAS를 쓸 시 모든 서버는 WAS의 종류와 버전, 설정을 일치시켜야 한다.
        //      즉, 새로운 서버가 추가되면 모든 서버가 같은 WAS 환경을 구축해야한다.
        //      ex) 1대의 서버가 아닌 30대의 서버에서 이와 같이 추가되면 시간을 많이 잡아먹게 된다.
    }
}
