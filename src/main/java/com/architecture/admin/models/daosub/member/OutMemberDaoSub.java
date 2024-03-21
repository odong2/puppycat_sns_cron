package com.architecture.admin.models.daosub.member;

import com.architecture.admin.models.dto.member.MemberDto;
import com.architecture.admin.models.dto.member.OutMemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OutMemberDaoSub {

    /*****************************************************
     * Select
     ****************************************************/
    /**
     * 탈퇴 회원 리스트 가져오기
     *
     * @param day 탈퇴 기준 날짜
     * @return 탈퇴 대기 List
     */
    List<OutMemberDto> getOutMember(String day);

    /**
     * 간편가입 정보 조회하기
     *
     * @param outMemberDto memberIdx
     * @return simple_type , auth_token
     */
    MemberDto getSimpleInfo(OutMemberDto outMemberDto);

    /**
     * 콘텐츠 신고 내역 가져오기
     *
     * @param outMemberDto memberIdx
     * @return 신고 idx 리스트
     */
    List<Long> selectContentsReport(OutMemberDto outMemberDto);

    /**
     * 댓글 신고 내역 가져오기
     *
     * @param outMemberDto memberIdx
     * @return 신고 idx 리스트
     */
    List<Long> selectCommentReport(OutMemberDto outMemberDto);

    /**
     * 콘텐츠 좋아요 내역 가져오기
     *
     * @param outMemberDto memberIdx
     * @return 좋아요 idx 리스트
     */
    List<Long> selectContentsLike(OutMemberDto outMemberDto);

    /**
     * 댓글 좋아요 내역 가져오기
     *
     * @param outMemberDto memberIdx
     * @return 좋아요 idx 리스트
     */
    List<Long> selectCommentLike(OutMemberDto outMemberDto);

    /**
     * 콘텐츠 좋아요한 idx 가져오기
     *
     * @param outMemberDto memberIdx
     * @return 콘텐츠 idx 리스트
     */
    List<Long> selectContentsIdxLike(OutMemberDto outMemberDto);

    /**
     * 댓글 좋아요한 idx 가져오기
     *
     * @param outMemberDto memberIdx
     * @return 댓글 idx 리스트
     */
    List<Long> selectCommentIdxLike(OutMemberDto outMemberDto);

    /**
     * 저장한 콘텐츠 idx 가져오기
     *
     * @param outMemberDto memberIdx
     * @return 콘텐츠 idx 리스트
     */
    List<Long> selectSaveContentsIdx(OutMemberDto outMemberDto);

    /**
     * 작성한 콘텐츠 idx 가져오기
     *
     * @param outMemberDto memberIdx
     * @return 콘텐츠 idx 리스트
     */
    List<Long> selectMyContentsIdx(OutMemberDto outMemberDto);

    /**
     * 작성한 댓글 idx 가져오기
     *
     * @param outMemberDto memberIdx
     * @return 댓글 idx 리스트
     */
    List<Long> selectMyCommentIdx(OutMemberDto outMemberDto);

    /**
     * follow member uuid get
     */
    List<String> getFollowMemberUuidList(OutMemberDto outMemberDto);

    /**
     * follow member uuid get
     */
    List<String> getFollowMeMemberUuidList(OutMemberDto outMemberDto);

}
