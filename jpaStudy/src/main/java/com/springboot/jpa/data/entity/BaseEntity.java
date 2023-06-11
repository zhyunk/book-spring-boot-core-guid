package com.springboot.jpa.data.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass // JPA 엔티티 클래스가 상속받을 경우 자식 클래스에게 매핑 정보를 전달한다.
@EntityListeners(AuditingEntityListener.class) // 엔티티를 데이터베이스에 적용하기 전후로 콜백을 요청할 수 있게 한다.
public class BaseEntity {

    @CreatedDate // 데이터 생성 날짜 자동 주입
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // 데이터 수정 날짜 자동 주입
    private LocalDateTime updatedAt;

}
