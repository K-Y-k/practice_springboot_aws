package com.kyk.book.springboot.domain.posts;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 반복적으로 사용되는 필드를 엔티티에 상속시키기 위한 클래스로
 * 이 클래스는 모든 엔티티의 상위 클래스가 되어 엔티티들의 생성일, 수정일을 자동으로 관리하는 역할을 한다.
 */
@Getter
@MappedSuperclass     // JPA 엔티티 클래스들이 BaseTimeEntity에 상속할 경우 아래 필드들도 칼럼으로 인식하게 한다.
@EntityListeners(AuditingEntityListener.class) // BaseTimeEntity 클래스에 Auditing 기능을 포함시킨다.
public abstract class BaseTimeEntity {

    @CreatedDate      // 엔티티가 생성되어 저장될 때 시간이 자동 저장된다.
    private LocalDateTime createdDate;

    @LastModifiedDate // 조회한 엔티티의 값을 변경할 때 시간이 자동 저장된다.
    private LocalDateTime modifiedDate;
}
