package com.green.greengram4.feed;

import com.green.greengram4.entity.FeedCommentEntity;
import com.green.greengram4.entity.FeedEntity;
import com.green.greengram4.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedCommentRepository extends JpaRepository<FeedCommentEntity, Long> {
    @EntityGraph(attributePaths = {"userEntity"}) //조인걸어서 가져온겠다
    List<FeedCommentEntity> findAllTop4ByFeedEntity(FeedEntity feedEntity);
}
