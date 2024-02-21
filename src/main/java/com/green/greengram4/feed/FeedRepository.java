package com.green.greengram4.feed;

import com.green.greengram4.entity.FeedEntity;
import com.green.greengram4.entity.UserEntity;
import jakarta.persistence.Entity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<FeedEntity,Long>, FeedQdslRepository { //레파지토리와연결되는 테이블 , pk타입
    @EntityGraph(attributePaths = {"userEntity"}) //그래프 탐색으로 가져올 멤버필드
    List<FeedEntity> findAllByUserEntityOrderByIfeedDesc(UserEntity userEntity, Pageable pageable);
    //UserEntity 뒤부터 찾아주는 ,,? 그런거

}
