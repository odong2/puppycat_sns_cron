package com.architecture.admin.services.member;

import com.architecture.admin.models.dao.member.OutMemberDao;
import com.architecture.admin.models.daosub.member.OutMemberDaoSub;
import com.architecture.admin.models.dto.member.OutMemberDto;
import com.architecture.admin.services.BaseService;
import com.architecture.admin.services.SQSService;
import com.architecture.admin.services.comment.CommentService;
import com.architecture.admin.services.contents.ContentsService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "use.sqs.listener.enabled", havingValue = "true")
public class OutMemberService extends BaseService {

    private final CommentService commentService;
    private final ContentsService contentsService;
    private final OutMemberDao outMemberDao;
    private final OutMemberDaoSub outMemberDaoSub;
    private final SQSService sqsService;
    @Value("${cloud.aws.sns.out.simple.member.url.arn}")
    private String sqsARN;
    @Value("${env.server}")
    private String server;  // 서버

    /*****************************************************
     *  Modules
     ****************************************************/
    /**
     * 회원 탈퇴
     *
     * @param message sqs 메시지
     * @param headers
     */
    @Transactional
    @SqsListener(value = {"${cloud.aws.sqs.admin.out.member.listener}"}, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void outMember(@Payload String message, @Headers Map<String, String> headers) {
        try {
            // 큐 data
            JSONObject obj = new JSONObject(message);
            String memberUuid = obj.getString("memberUuid");
            Integer isSimple = obj.getInt("isSimple");
            pushAlarm(server + " [snsCron] memberUuid = " + memberUuid + "시작");
            OutMemberDto outMemberDto = new OutMemberDto();
            outMemberDto.setUuid(memberUuid);

            // 댓글 신고 내역 조회
            List<Long> commentReportIdxList = selectReportIdx(outMemberDto, "comment");

            // 댓글 신고 내역 삭제 [sns_contents_comment_report]
            if (!commentReportIdxList.isEmpty()) {
                deleteCommentReport(commentReportIdxList);
            }

            // 댓글 좋아요 내역 조회
            List<Long> commentLikeIdxList = selectLikeIdx(outMemberDto, "comment");

            if (!commentLikeIdxList.isEmpty()) {
                // 좋아요한 댓글 idx 리스트 조회
                List<Long> commentIdxList = selectLikeContentsIdx(outMemberDto, "comment");

                // cnt 차감 [sns_contents_like_cnt]
                deleteCommentLikeCnt(commentIdxList);

                // 댓글 좋아요 삭제 [sns_contents_comment_like]
                deleteCommentLike(outMemberDto);
            }

            // 댓글 idx 가져오기 state = 1
            List<Long> getCommentContentsIdxList = commentService.getCommentContentsIdxList(memberUuid);

            // idx가 존재 한다면 comment cnt 차감
            if (!getCommentContentsIdxList.isEmpty()) {
                deleteContentsCommentCnt(getCommentContentsIdxList);
            }

            // 댓글 삭제
            commentService.updateDelCommentState(memberUuid);

            // 콘텐츠 신고 내역 조회
            List<Long> contentsReportIdxList = selectReportIdx(outMemberDto, "contents");

            // 콘텐츠 신고 내역 삭제 [sns_contents_report] / [sns_contents_report_reason]
            if (!contentsReportIdxList.isEmpty()) {
                deleteContentsReport(contentsReportIdxList);
            }

            // 콘텐츠 좋아요 내역 조회
            List<Long> contentsLikeIdxList = selectLikeIdx(outMemberDto, "contents");

            if (!contentsLikeIdxList.isEmpty()) {

                // 좋아요한 콘텐츠 idx 리스트 조회 (cnt 차감)
                List<Long> contentsIdxList = selectLikeContentsIdx(outMemberDto, "contents");

                // cnt 차감 [sns_contents_like_cnt]
                deleteContentsLikeCnt(contentsIdxList);

                // 콘텐츠 좋아요 삭제 [sns_contents_like]
                deleteContentsLike(outMemberDto);
            }

            // 컨텐츠 삭제
            contentsService.updateDelContentsState(memberUuid);

            // 내가 팔로우 한 유저 memberUuid 가져오기
            List<String> getFollowMemberUuidList = outMemberDaoSub.getFollowMemberUuidList(outMemberDto);

            if (!getFollowMemberUuidList.isEmpty()) {
                // 내가 팔로우 한거 삭제
                deleteMemberFollow(outMemberDto);

                // 내가 팔로우 한거 cnt 차감
                deleteMemberFollowCnt(getFollowMemberUuidList);
            }

            //나를 팔로운 한 유저 memberUuid 가져오기
            List<String> getFollowMeMemberUuidList = outMemberDaoSub.getFollowMeMemberUuidList(outMemberDto);

            if (!getFollowMeMemberUuidList.isEmpty()) {
                // 나를 팔로우 한거 삭제
                deleteMemberFollowMe(outMemberDto);

                // 나를 팔로우 한 사람 cnt 차감
                deleteMemberFollowMeCnt(getFollowMeMemberUuidList);
            }

            // 내 팔로우 팔로워잉 0
            deleteMyMemberFollowFollowerCnt(outMemberDto);

            pushAlarm(server + " [snsCron] memberUuid = " + memberUuid + "완료");

            if (isSimple == 1) {
                // DeduplicationId 회원 IDX + "simple_member_out"
                String dpId = memberUuid + "simple_member_out";

                // SQS push (delete 로직 수행)
                sqsService.publish(obj.toString(), sqsARN, "delSimpleMemberInfo", dpId);
            }
        } catch (Exception e) {
            pushAlarm("Sns Cron outMember : Sqs message = " + message + "Exception Message = " + e.getMessage());
        }
    }

    /**
     * 신고 내역 조회
     *
     * @param outMemberDto memberIdx
     * @param type 신고 type
     * @return 신고 idx 리스트
     */
    public List<Long> selectReportIdx(OutMemberDto outMemberDto, String type) {
        List<Long> idxList = null;

        if (type.equals("contents")) {
            idxList = outMemberDaoSub.selectContentsReport(outMemberDto);
        }
        else if (type.equals("comment")) {
            idxList = outMemberDaoSub.selectCommentReport(outMemberDto);
        }

        return idxList;
    }

    /**
     * 댓글 신고 내역 삭제
     *
     * @param idxList 신고 idx 리스트
     */
    public void deleteCommentReport(List<Long> idxList) {
        outMemberDao.deleteCommentReport(idxList);
    }

    /**
     * 좋아요 내역 조회
     *
     * @param outMemberDto memberIdx
     * @param type 좋아요 type
     * @return 좋아요 idx 리스트
     */
    public List<Long> selectLikeIdx(OutMemberDto outMemberDto, String type) {
        List<Long> idxList = null;

        if (type.equals("contents")) {
            idxList = outMemberDaoSub.selectContentsLike(outMemberDto);
        }
        else if (type.equals("comment")) {
            idxList = outMemberDaoSub.selectCommentLike(outMemberDto);
        }

        return idxList;
    }

    /**
     * 좋아요한 콘텐츠 idx 조회
     *
     * @param outMemberDto memberIdx
     * @param type 좋아요 type
     * @return 좋아요한 콘텐츠 idx 리스트
     */
    public List<Long> selectLikeContentsIdx(OutMemberDto outMemberDto, String type) {
        List<Long> idxList = null;

        if (type.equals("contents")) {
            idxList = outMemberDaoSub.selectContentsIdxLike(outMemberDto);
        }
        else if (type.equals("comment")) {
            idxList = outMemberDaoSub.selectCommentIdxLike(outMemberDto);
        }

        return idxList;
    }

    /**
     * 댓글 좋아요 cnt 차감
     *
     * @param idxList 댓글 idx 리스트
     */
    public void deleteContentsCommentCnt(List<Long> idxList) {
        outMemberDao.updateContentsCommentCnt(idxList);
    }

    /**
     * 댓글 좋아요 cnt 차감
     *
     * @param idxList 댓글 idx 리스트
     */
    public void deleteCommentLikeCnt(List<Long> idxList) {
        outMemberDao.updateCommentLikeCnt(idxList);
    }

    /**
     * 댓글 좋아요 삭제
     */
    public void deleteCommentLike(OutMemberDto outMemberDto) {
        outMemberDao.deleteCommentLike(outMemberDto);
    }

    /**
     * 콘텐츠 좋아요 cnt 차감
     * @param idxList 콘텐츠 idx 리스트
     */
    public void deleteContentsLikeCnt(List<Long> idxList) {
        outMemberDao.updateContentsLikeCnt(idxList);
    }

    /**
     * 콘텐츠 좋아요 삭제
     */
    public void deleteContentsLike(OutMemberDto outMemberDto) {
        outMemberDao.deleteContentsLike(outMemberDto);
    }

    /**
     * 콘텐츠 신고 내역 삭제
     * @param idxList 신고 idx 리스트
     */
    public void deleteContentsReport(List<Long> idxList) {
        outMemberDao.deleteContentsReport(idxList);
    }

    /**
     * 팔로워 삭제
     * @param outMemberDto memberIdx
     */
    public void deleteMemberFollow(OutMemberDto outMemberDto) {
        outMemberDao.deleteMemberFollow(outMemberDto);
    }

    /**
     * 팔로워 삭제
     * @param outMemberDto memberIdx
     */
    public void deleteMemberFollowMe(OutMemberDto outMemberDto) {
        outMemberDao.deleteMemberFollowMe(outMemberDto);
    }

    /**
     * 팔로워 cnt 차감
     */
    public void deleteMemberFollowCnt(List<String> uuidList) {
        outMemberDao.deleteMemberFollowCnt(uuidList);
    }

    /**
     * 팔로워 cnt 차감
     */
    public void deleteMemberFollowMeCnt(List<String> uuidList) {
        outMemberDao.deleteMemberFollowMeCnt(uuidList);
    }

    // 내 팔로우 팔로워잉 0
    public void deleteMyMemberFollowFollowerCnt(OutMemberDto outMemberDto) {
        outMemberDao.deleteMyMemberFollowFollowerCnt(outMemberDto);
    }
}
