package com.architecture.admin.models.dao.member;

import com.architecture.admin.models.dto.member.OutMemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OutMemberDao {


    /*****************************************************
     * Update
     ****************************************************/
    /**
     * 탈퇴 상태 업데이트
     *
     * @param outMemberDto memberIdx, outDate
     */
     void updateOutState(OutMemberDto outMemberDto);

    /**
     * 콘텐츠 코멘트 cnt 차감
     *
     * @param idxList 콘텐츠 idx 리스트
     */
    void updateContentsCommentCnt(List<Long> idxList);

    /**
     * 콘텐츠 좋아요 cnt 차감
     *
     * @param idxList 콘텐츠 idx 리스트
     */
    void updateContentsLikeCnt(List<Long> idxList);

    /**
     * 댓글 좋아요 cnt 차감
     *
     * @param idxList 댓글 idx 리스트
     */
    void updateCommentLikeCnt(List<Long> idxList);

    /**
     * 콘텐츠 저장 cnt 차감
     *
     * @param idxList 콘텐츠 idx 리스트
     */
    void updateContentsSaveCnt(List<Long> idxList);

    /**
     * 댓글 cnt 차감
     *
     * @param idxList 콘텐츠 idx 리스트
     */
    void updateCommentCnt(List<Long> idxList);

    /**
     * member table update
     *
     * @param outMemberDto
     */
    void updateDelMember(OutMemberDto outMemberDto);

    /**
     * member_app table update
     *
     * @param outMemberDto
     */
    void updateDelMemberApp(OutMemberDto outMemberDto);

    /**
     * member_out table update
     *
     * @param outMemberDto
     */
    void updateDelMemberOut(OutMemberDto outMemberDto);

    /*****************************************************
     * Delete
     ****************************************************/
    /**
     * 회원 삭제
     *
     * @param outMemberDto memberIdx
     */
    void deleteMember(OutMemberDto outMemberDto);

    /**
     * 간편회원 삭제
     *
     * @param outMemberDto memberIdx
     */
    void deleteMemberSimple(OutMemberDto outMemberDto);

    /**
     * 비밀번호 삭제
     *
     * @param outMemberDto memberIdx
     */
    void deleteMemberPassword(OutMemberDto outMemberDto);

    /**
     * 회원 정보 삭제
     *
     * @param outMemberDto memberIdx
     */
    void deleteMemberInfo(OutMemberDto outMemberDto);

    /**
     * 콘텐츠 신고 내역 삭제
     *
     * @param idxList 신고 idx List
     */
    void deleteContentsReport(List<Long> idxList);

    /**
     * 댓글 신고 내역 삭제
     *
     * @param idxList 신고 idx List
     */
    void deleteCommentReport(List<Long> idxList);

    /**
     * 콘텐츠 신고 상세사유 삭제
     *
     * @param idxList 신고 idx List
     */
    void deleteContentsReportReason(List<Long> idxList);

    /**
     * 댓글 신고 상세사유 삭제
     *
     * @param idxList 신고 idx List
     */
    void deleteCommentReportReason(List<Long> idxList);

    /**
     * 콘텐츠 좋아요 삭제
     */
    void deleteContentsLike(OutMemberDto outMemberDto);

    /**
     * 댓글 좋아요 삭제
     */
    void deleteCommentLike(OutMemberDto outMemberDto);

    /**
     * 댓글 삭제
     *
     * @param idxList 댓글 idx 리스트
     */
    void deleteComment(List<Long> idxList);

    /**
     * 콘텐츠 숨김 내역 삭제
     *
     * @param outMemberDto memberIdx
     */
    void deleteContentsHide(OutMemberDto outMemberDto);

    /**
     * 콘텐츠 저장 내역 삭제
     *
     * @param outMemberDto memberIdx
     */
    void deleteContentsSave(OutMemberDto outMemberDto);

    void deleteCommentHashTagMapping(List<Long> idxList);
    void deleteCommentMentionMapping(List<Long> idxList);

    /**
     * 콘텐츠 위치 매핑 삭제
     *
     * @param idxList 콘텐츠 idx 리스트
     */
    void deleteContentsLocationMapping(List<Long> idxList);

    /**
     * 콘텐츠 해시태그 매핑 삭제
     *
     * @param idxList 콘텐츠 idx 리스트
     */
    void deleteContentsHashTagMapping(List<Long> idxList);

    /**
     * 콘텐츠 멘션 매핑 삭제
     *
     * @param idxList 콘텐츠 idx 리스트
     */
    void deleteContentsMentionMapping(List<Long> idxList);

    /**
     * 콘텐츠 이미지 내 태그 매핑 삭제
     *
     * @param idxList 콘텐츠 idx 리스트
     */
    void deleteContentsImgMemberTagMapping(List<Long> idxList);

    /**
     * 콘텐츠 이미지 삭제
     *
     * @param idxList 콘텐츠 idx 리스트
     */
    void deleteContentImg(List<Long> idxList);

    /**
     * 콘텐츠 삭제
     *
     * @param idxList 콘텐츠 idx 리스트
     */
    void deleteContents(List<Long> idxList);

    /**
     * 차단 내역 삭제
     *
     * @param outMemberDto memberIdx
     */
    void deleteMemberBlock(OutMemberDto outMemberDto);

    /**
     * 제재 내역 삭제
     *
     * @param outMemberDto memberIdx
     */
    void deleteMemberRestrain(OutMemberDto outMemberDto);

    /**
     * 팔로우/팔로워 삭제
     *
     * @param outMemberDto memberIdx
     */
    void deleteMemberFollow(OutMemberDto outMemberDto);

    /**
     * 팔로우/팔로워 삭제
     *
     * @param outMemberDto memberIdx
     */
    void deleteMemberFollowMe(OutMemberDto outMemberDto);

    /**
     * 팔로워 cnt 차감
     */
    void deleteMemberFollowCnt(List<String> uuidList);

    /**
     * 팔로워 cnt 차감
     */
    void deleteMemberFollowMeCnt(List<String> uuidList);

    void deleteMyMemberFollowFollowerCnt(OutMemberDto outMemberDto);

    /**
     * 프로필 소개글 삭제
     *
     * @param outMemberDto memberIdx
     */
    void deleteMemberProfileIntro(OutMemberDto outMemberDto);

    /**
     * 프로필 소개글 로그 삭제
     *
     * @param outMemberDto memberIdx
     */
    void deleteMemberProfileIntroLog(OutMemberDto outMemberDto);

    /**
     * 프로필 이미지 삭제
     *
     * @param outMemberDto memberIdx
     */
    void deleteMemberProfileImg(OutMemberDto outMemberDto);

    /**
     * 프로필 소개글 로그 삭제
     *
     * @param outMemberDto memberIdx
     */
    void deleteMemberProfileImgLog(OutMemberDto outMemberDto);

    /**
     * 회원 알림함 삭제
     *
     * @param outMemberDto memberIdx
     */
    void deleteMemberNoti(OutMemberDto outMemberDto);

    /**
     * 회원 app 정보 삭제
     *
     * @param outMemberDto memberIdx
     */
    void deleteMemberApp(OutMemberDto outMemberDto);

    /**
     * 회원 access 정보 삭제
     *
     * @param outMemberDto memberIdx
     */
    void deleteMemberAccess(OutMemberDto outMemberDto);

}
