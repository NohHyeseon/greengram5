package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "t_feed_pics")
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class FeedPicsEntity extends CreatedAtEntity{
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ifeedPics;

    @ManyToOne
    @JoinColumn(columnDefinition = "BIGINT UNSIGNED", nullable = false, name ="ifeed")
    private FeedEntity feedEntity;

    @Column(name = "pic",length = 2100)
    private String pic;




}
