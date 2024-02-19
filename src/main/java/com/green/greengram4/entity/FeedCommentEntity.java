package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name ="t_feed_comment")
public class FeedCommentEntity extends BaseEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED",name ="ifeed_comment")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ifeedCommnet;

    @ManyToOne //한명의 유저가 여러개의 댓글을 달 수 있다. one의 유저가 many댓글을 달 수 있다.
    @JoinColumn(name ="iuser", nullable = false)
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name ="ifeed", nullable = false)
    private FeedEntity feedEntity;


    @Column(length = 500, nullable = false)
    private String comment;

}
