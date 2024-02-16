package com.green.greengram4.feed;

import com.google.firebase.database.core.Repo;
import com.green.greengram4.common.Const;
import com.green.greengram4.common.MyFileUtils;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.entity.FeedEntity;
import com.green.greengram4.entity.FeedPicsEntity;
import com.green.greengram4.entity.UserEntity;
import com.green.greengram4.exception.FeedErrorCode;
import com.green.greengram4.exception.RestApiException;
import com.green.greengram4.feed.model.*;
import com.green.greengram4.security.AuthenticationFacade;
import com.green.greengram4.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper mapper;
    private final FeedPicsMapper picsMapper;
    private final FeedFavMapper favMapper;
    private final FeedCommentMapper commentMapper;
    private final FeedRepository repository;
    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;
    private final MyFileUtils myFileUtils;


    @Transactional
    public FeedPicsInsDto postFeed(FeedInsDto dto) {
        if (dto.getPics() == null) {
            throw new RestApiException(FeedErrorCode.PICS_MORE_THEN_ONE);
        }
        //iuser가져오기
        UserEntity userEntity =
                userRepository.getReferenceById((long) authenticationFacade.getLoginUserPk()); //영속 셀렉트없이도pk가 들어가있음


        FeedEntity feedEntity = new FeedEntity();
        feedEntity.setUserEntity(userEntity);
        feedEntity.setContents(dto.getContents());
        feedEntity.setLocation(dto.getLocation());
        repository.save(feedEntity);


        String target = "/feed/" + feedEntity.getIfeed();

        FeedPicsInsDto pDto = new FeedPicsInsDto();
        pDto.setIfeed(feedEntity.getIfeed().intValue()); //type 맞춰주기
        for (MultipartFile file : dto.getPics()) {
            String saveFileNm = myFileUtils.transferTo(file, target);
            pDto.getPics().add(saveFileNm);
        }

        List<FeedPicsEntity> feedPicsEntityList = pDto.getPics()
                .stream()
                .map(item -> FeedPicsEntity.builder()
                        .pic(item) //item으로 사진이 들어왔음
                        .feedEntity(feedEntity)
                        .build() )
                .collect(Collectors.toList());
        feedEntity.getFeedPicsEntityList().addAll(feedPicsEntityList);
        return pDto;
    }


//    @Transactional
//    public FeedPicsInsDto postFeed(FeedInsDto dto) {
//        if(dto.getPics() == null) {
//            throw new RestApiException(FeedErrorCode.PICS_MORE_THEN_ONE);
//        }
//
//        dto.setIuser(authenticationFacade.getLoginUserPk());
//        log.info("dto: {}", dto);
//        int feedAffectedRows = mapper.insFeed(dto);
//        String target = "/feed/" + dto.getIfeed();
//
//        FeedPicsInsDto pDto = new FeedPicsInsDto();
//        pDto.setIfeed(dto.getIfeed());
//        for(MultipartFile file : dto.getPics()) {
//            String saveFileNm = myFileUtils.transferTo(file, target);
//            pDto.getPics().add(saveFileNm);
//        }
//        int feedPicsAffectedRows = picsMapper.insFeedPics(pDto);
//        return pDto;
//    }

    public List<FeedSelVo> getFeedAll(FeedSelDto dto, Pageable pageable) {
        List<FeedSelVo> list = null;
        if(dto.getIsFavList() == 0 && dto.getTargetIuser()>0){
            UserEntity userEntity = new UserEntity();
            userEntity.setIuser( (long)dto.getLoginedIuser());
            List<FeedEntity> feedEntityList = repository.findAllByUserEntityOrderByIfeedDesc(userEntity,pageable);

        }
        System.out.println("!!!!!");



         mapper.selFeedAll(dto);


        FeedCommentSelDto fcDto = new FeedCommentSelDto();
        fcDto.setStartIdx(0);
        fcDto.setRowCount(Const.FEED_COMMENT_FIRST_CNT);

        for (FeedSelVo vo : list) {
            List<String> pics = picsMapper.selFeedPicsAll(vo.getIfeed());
            vo.setPics(pics);

            fcDto.setIfeed(vo.getIfeed());
            List<FeedCommentSelVo> comments = commentMapper.selFeedCommentAll(fcDto);
            vo.setComments(comments);

            if (comments.size() == Const.FEED_COMMENT_FIRST_CNT) {
                vo.setIsMoreComment(1);
                comments.remove(comments.size() - 1);
            }
        }
        return list;
    }



//    public List<FeedSelVo> getFeedAll(FeedSelDto dto) {
//        System.out.println("!!!!!");
//        List<FeedSelVo> list = mapper.selFeedAll(dto);
//
//        FeedCommentSelDto fcDto = new FeedCommentSelDto();
//        fcDto.setStartIdx(0);
//        fcDto.setRowCount(Const.FEED_COMMENT_FIRST_CNT);
//
//        for (FeedSelVo vo : list) {
//            List<String> pics = picsMapper.selFeedPicsAll(vo.getIfeed());
//            vo.setPics(pics);
//
//            fcDto.setIfeed(vo.getIfeed());
//            List<FeedCommentSelVo> comments = commentMapper.selFeedCommentAll(fcDto);
//            vo.setComments(comments);
//
//            if (comments.size() == Const.FEED_COMMENT_FIRST_CNT) {
//                vo.setIsMoreComment(1);
//                comments.remove(comments.size() - 1);
//            }
//        }
//        return list;
//    }


    public ResVo delFeed(FeedDelDto dto) {
        //1 이미지
        int picsAffectedRows = picsMapper.delFeedPicsAll(dto);
        if (picsAffectedRows == 0) {
            return new ResVo(Const.FAIL);
        }

        //2 좋아요
        int favAffectedRows = favMapper.delFeedFavAll(dto);

        //3 댓글
        int commentAffectedRows = commentMapper.delFeedCommentAll(dto);

        //4 피드
        int feedAffectedRows = mapper.delFeed(dto);
        return new ResVo(Const.SUCCESS);
    }

    //--------------- t_feed_fav
    public ResVo toggleFeedFav(FeedFavDto dto) {
        //ResVo - result값은 삭제했을 시 (좋아요 취소) 0, 등록했을 시 (좋아요 처리) 1
        int delAffectedRows = favMapper.delFeedFav(dto);
        if (delAffectedRows == 1) {
            return new ResVo(Const.FEED_FAV_DEL);
        }
        int insAffectedRows = favMapper.insFeedFav(dto);
        return new ResVo(Const.FEED_FAV_ADD);
    }
}
