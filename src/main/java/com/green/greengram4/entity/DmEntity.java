package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "t_dm")
public class DmEntity extends CreatedAtEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    //bigint unsigned, => 부호없음하고 싶으면
    private Long idm;

    @Column(length = 2000, name = "last_msg")
    private String lastMsg;

    @LastModifiedDate //??
    @Column(name ="last_msg_at")
    private LocalDateTime dateTime;





}
