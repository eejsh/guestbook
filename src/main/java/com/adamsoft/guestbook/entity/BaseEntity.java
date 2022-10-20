package com.adamsoft.guestbook.entity;


import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

//Entity로 사용할 수 있지만 Table을 생성하지는 않음.
@MappedSuperclass

//JPA를 감시하고 있다가 데이터를 수정하게 만듬
@EntityListeners(value = {AuditingEntityListener.class})
//감시하고있다가 인스턴스 호출될때 감시
@Getter
public abstract class BaseEntity {
//인스턴스를 만들지 않기 위해 abstract class로 설정함.

    @CreatedDate //데이터 생성날짜를 설정
    @Column(name="regdate", updatable = false) //저장할 때 이름은 regdate, 수정 불가
    private LocalDateTime regDate;

    @LastModifiedDate //데이터의 마지막 수정 날짜를 설정
    @Column(name="moddate") //저장할때 이름은 moddate, 수정 가능.
    private LocalDateTime modDate;
}
