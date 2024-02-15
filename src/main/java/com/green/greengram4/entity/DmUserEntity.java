package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_dm_user")
public class DmUserEntity {
    @EmbeddedId
    private DmUserIds dmUserIds;

    @ManyToOne
    @MapsId("iuser")
    @JoinColumn(columnDefinition = "BIGINT UNSIGNED", name ="iuser") //복합키시 name줘야함
    private UserEntity userEntity;

    @ManyToOne
    @MapsId("idm")
    @JoinColumn(columnDefinition = "BIGINT UNSIGNED", name ="idm")
    private DmEntity dmEntity;


}
