package com.green.greengram4.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.LocalDateTime;

@Data
@MappedSuperclass //컬럼들이 상속받게 해줌
@EntityListeners(AuditingEntityListener.class) // lister가 event(~가 발생했을 때) entity를 적용 전 콜백으로 auditingentity를 호출해 공통적으로 처리해줌
//created_at, updated_at 이 바뀔 때 마다 적용 시켜줌
public class BaseEntity {
    @CreatedDate
    @Column(updatable = false, nullable = false) //최초 한번만 실행되기 때문에 udpate=false
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
