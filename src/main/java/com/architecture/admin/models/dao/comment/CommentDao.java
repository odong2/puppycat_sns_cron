package com.architecture.admin.models.dao.comment;

import com.architecture.admin.models.dto.comment.CommentDto;
import com.architecture.admin.models.dto.member.OutMemberDto;

public interface CommentDao {

    /*****************************************************
     * Update
     ****************************************************/
    /**
     * 댓글 상태값 수정
     *
     * @param commentDto idx state
     * @return
     */
    int updateDelCommentState(String memberUuid);

    /*****************************************************
     * Delete
     ****************************************************/

}
