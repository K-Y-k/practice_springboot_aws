package com.kyk.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * MyBatis 등에서 Dao라고 불리는 DB Layer 접근자로
 * JPA에서는 Repository라고 부르며 인터페이스로 생성한다.
 * 단순히 인터페이스를 생성 후, JpaRepository<에닡티 클래스, PK타입>으로 상속하면 기본 CRUD 메소드가 자동으로 생성된다.
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();

}
