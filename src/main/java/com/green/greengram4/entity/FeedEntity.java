package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_feed")
public class FeedEntity extends BaseEntity{
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ifeed;

    @ManyToOne
    @JoinColumn(name ="iuser", nullable = false)
    private UserEntity userEntity;


    @Column(length = 1000, name = "contents")
    private String contents;

    @Column(length = 30, name = "location")
    private String location;
}
