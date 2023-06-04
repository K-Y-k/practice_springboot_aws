package com.kyk.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor    // 기본 생성자 자동 추가
@Entity               // 주요한 어노테이션 기준으로 가까이 둔다.
public class Posts extends BaseTimeEntity {  // 실제 DB 테이블과 매칭될 클래스

    @Id // 해당 테이블의 PK 필드를 나타낸다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 생성 규칙을 나타낸다. GenerationType.IDENTITY 옵션을 추가해야만 auto increment가 된다.
    private Long id;

    @Column(length = 500, nullable = false)             // @Column으로 선언하면 해당 필드는 컬럼이 된다. 선언 생략해도 된다.
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false) //
    private String content;

    private String author;

    @Builder // 해당 클래스의 빌더 패턴 클래스 생성, 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }


    // 특이점 : setter 메소드가 없다.
    // getter/setter가 무작정 생성된 경우가 있다.
    // 이렇게 되면 해당 클래스의 인스턴스 값들이 언제 어디서 변해야하는지 코드상 구분할 수 없어 기능 변경 시 복잡해진다.

    // 그래서 엔티티 클래스에는 절대 setter 메소드를 만들지 않는다.
    // 단, 필드 값 변경이 필요하면 그 목적과 의도를 나타낼 수 있는 메소드를 추가해야 한다.
    // ex)
    // public void cancelOrder() {
    //     this.status = false;
    // }


    // 위 예시 의문: setter가 없는 상황에서 ()안에 매개변수도 없는데 어떻게 값을 채워 DB에 삽입하나?
    // 기본적인 구조는 생성자를 통해 최종값을 채운 후 DB에 삽입하는 것이고
    // 값 변경이 필요한 경우 해당 이벤트에 맞는 public 메소드를 호출하여 변경하는 것을 전제로 한다.
    // 여기서는 생성자 대신 @Builder를 통해 제공되는 빌더 클래스를 사용한다.
    // 생성자나 빌더나 생성 시점에 값을 채워주는 역할은 똑같다.

    // 생상자의 안좋은점 ex) a, b가 바뀌어도 싱행 전까진 모른다.
    // public Example(String a, String b) {
    //     this.a = a;
    //     this.b = b;
    // }

    // 빌더를 활용하면 좋은점 ex) 어떤 필드에 어떤 값을 채워야할지 명확하게 인지가 가능하다.
    // Example.builder()
    //        .a(a)
    //        .b(b)
    //        .build()
    
    
    // 연관 편의 메서드
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
