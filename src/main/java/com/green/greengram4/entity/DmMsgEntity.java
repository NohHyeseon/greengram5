package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.stream.Stream;

@Data
@Entity
@Table(name = "t_dm_msg")
public class DmMsgEntity extends CreatedAtEntity {

    @EmbeddedId
    private DmMsgIds dmMsgIds;

    @ManyToOne
    @MapsId("idm") //복합키에 있는 colum을 컬어야해서 mpasid를 사용해줌
    @JoinColumn(columnDefinition = "BIGINT UNSIGNED", name = "idm")
    private DmEntity dmEntity;

    @ManyToOne//one이 상대방 Many -> msg
    @JoinColumn(name = "iuser", nullable = false)
    private UserEntity userEntity;


    @Column(length = 2000, nullable = false)
    private String msg;


}
