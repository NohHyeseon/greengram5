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
@EntityListeners(AuditingEntityListener.class) // lister가 event(~가 발생했을 때) entity를 적용 전 콜백으로 auditingentity를 호출해 공통적으로 처리해줌
//created_at, updated_at 이 바뀔 때 마다 적용 시켜줌
public class BaseEntity extends CreatedAtEntity {

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
