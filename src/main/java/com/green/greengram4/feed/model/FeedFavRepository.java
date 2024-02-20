package com.green.greengram4.feed.model;


import com.green.greengram4.entity.FeedFavEntity;
import com.green.greengram4.entity.FeedFavIds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedFavRepository extends JpaRepository<FeedFavEntity, FeedFavIds> {

}
