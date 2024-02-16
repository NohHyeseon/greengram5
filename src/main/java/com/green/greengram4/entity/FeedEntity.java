package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

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

    @ToString.Exclude //String 변환할때 얘는 제외시키겠다
    @OneToMany(mappedBy = "feedEntity", cascade = CascadeType.PERSIST) //양방향 걸어주기 , 안적으면 컬럼하나 또 늘어남
    private List<FeedPicsEntity> feedPicsEntityList = new ArrayList<>();
}
