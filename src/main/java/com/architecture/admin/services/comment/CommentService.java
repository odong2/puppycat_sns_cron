package com.architecture.admin.services.comment;

import com.architecture.admin.models.dao.comment.CommentDao;
import com.architecture.admin.models.daosub.comment.CommentDaoSub;
import com.architecture.admin.services.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService extends BaseService {

    private final CommentDao commentDao;
    private final CommentDaoSub commentDaoSub;

    /*****************************************************
     *  SubFunction - Select
     ****************************************************/
    /**
     * 댓글 IDX 가져오기
     *
     * @param outMemberDto memberIdx
     * @return 좋아요 idx 리스트
     */
    public List<Long> getCommentContentsIdxList(String memberUuid) {
        return commentDaoSub.getCommentContentsIdxList(memberUuid);
    };

    /*****************************************************
     *  SubFunction - Update
     ****************************************************/
    /**
     * 댓글 상태값
     *
     * @param commentDto
     * @return CommentDto
     */
    public void updateDelCommentState(String memberUuid) {

        commentDao.updateDelCommentState(memberUuid);
    }


    /*****************************************************
     *  Common
     ****************************************************/

}
