package com.architecture.admin.models.daosub.noti;

import com.architecture.admin.models.dto.noti.ContentsNotiDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ContentsNotiDaoSub {
    /**
     * 컨텐츠 작성자 회원 정보
     *
     * @param contentsIdx 컨텐츠IDX
     * @return 컨텐츠 작성자 idx nick 작성regdate
     */
    ContentsNotiDto getContentsWriterInfo(Long contentsIdx);

    /**
     * 컨텐츠 첫번째 이미지 가져오기
     *
     * @param contentsIdx 컨텐츠IDX
     * @return 이미지url
     */
    String getContentsImg(Long contentsIdx);

    /**
     * 컨텐츠 등록자의 팔로워 리스트 가져오기
     *
     * @param contentsNotiDto memberUuid regdate
     * @return 팔로워 uuid
     */
    List<String> getFollowerMember(ContentsNotiDto contentsNotiDto);
}
