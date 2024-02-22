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

    @ManyToOne(fetch = FetchType.LAZY) //즉시 로딩시켜주는것
    @JoinColumn(name ="iuser", nullable = false)
    private UserEntity userEntity;


    @Column(length = 1000, name = "contents")
    private String contents;

    @Column(length = 30, name = "location")
    private String location;

    @ToString.Exclude //String 변환할때 얘는 제외시키겠다 ,스트링으로만들 때 연결고리를 끊어주는역할
    @OneToMany(mappedBy = "feedEntity", cascade = CascadeType.PERSIST) //양방향 걸어주기 , 안적으면 컬럼하나 또 늘어남
    private List<FeedPicsEntity> feedPicsEntityList = new ArrayList<>();
    //ㄱ현재 entity와 위 entity가 양방향이므로  @ToString.exclude가 없으면 무한루프 돌 수 있음

    @ToString.Exclude
    @OneToMany(mappedBy = "feedEntity")
    private List<FeedFavEntity> feedFavList = new ArrayList();

}
