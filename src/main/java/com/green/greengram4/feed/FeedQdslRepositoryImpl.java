package com.green.greengram4.feed;

import com.green.greengram4.entity.*;
import com.green.greengram4.feed.model.FeedSelDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.green.greengram4.entity.QFeedEntity.feedEntity;
import static com.green.greengram4.entity.QFeedFavEntity.feedFavEntity;
import static com.green.greengram4.entity.QFeedPicsEntity.feedPicsEntity;

@Slf4j
@RequiredArgsConstructor
public class FeedQdslRepositoryImpl implements FeedQdslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<FeedEntity> selFeedAll(FeedSelDto dto, Pageable pageable) {

        JPAQuery<FeedEntity> jpaQuery = jpaQueryFactory.select(feedEntity)
                //(whereTargetUser(targetIuser),whereTargetUser(targetIuser)) 쉼표로 and조건 사용가능
                .from(feedEntity)
                .join(feedEntity.userEntity).fetchJoin()
                .orderBy(feedEntity.ifeed.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());


        if (dto.getIsFavList() == 1) {
            //좋아요한 리스트만나오게
            jpaQuery.join(feedFavEntity)
                    .on(feedEntity.ifeed.eq(feedFavEntity.feedEntity.ifeed)
                            ,feedFavEntity.userEntity.iuser.eq(dto.getLoginIuser()));
        } else {
            jpaQuery.where(wheretargetUser(dto.getTargetIuser()));//프로필에 들어갔을 때 나오는
        }


        return jpaQuery.fetch();

//        return list.stream().map(
//                item -> FeedSelVo.builder()
//                        .ifeed(item.getIfeed().intValue())
//                        .contents(item.getContents())
//                        .location(item.getLocation())
//                        .createdAt(item.getCreatedAt().toString())
//                        .writerIuser(item.getUserEntity().getIuser().intValue())
//                        .writerNm(item.getUserEntity().getNm())
//                        .writerPic(item.getUserEntity().getPic()) //유저정보는 위에 join으로 가져오므로 한번 더 select이 안됨
//                        .pics(item.getFeedPicsEntityList().stream().map(
//                                pic -> pic.getPic()).collect(Collectors.toList())
//                        )
//                        .isFav(item.getFeedFavList().stream().anyMatch(
//                                fav -> fav.getUserEntity().getIuser() == dto.getLoginIuser()) ? 1 : 0
//                        )
//                        .build()
//        ).collect(Collectors.toList());

    }

    @Override
    public List<FeedPicsEntity> selFeedPicsAll(List<FeedEntity> feedEntityList) {
        return jpaQueryFactory.select(Projections.fields(FeedPicsEntity.class, feedPicsEntity.feedEntity, feedPicsEntity.pic))
                .from(feedPicsEntity)
                .where(feedPicsEntity.feedEntity.in(feedEntityList))
                .fetch();
    }

    @Override
    public List<FeedFavEntity> selFeedFavAllByMe(List<FeedEntity> feedEntityList, Long loginIuser) {
        return jpaQueryFactory.select(Projections.fields(FeedFavEntity.class
                        , feedFavEntity.feedEntity))
                .from(feedFavEntity)
                .where(feedFavEntity.feedEntity.in(feedEntityList)
                        , feedFavEntity.userEntity.iuser.eq(loginIuser))
                .fetch();
    }

    private BooleanExpression wheretargetUser(long targetIuser) {
        return targetIuser == 0 ? null : feedEntity.userEntity.iuser.eq(targetIuser); //
    }


}