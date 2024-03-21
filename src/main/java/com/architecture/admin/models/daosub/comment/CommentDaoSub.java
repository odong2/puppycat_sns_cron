package com.architecture.admin.models.daosub.comment;

import com.architecture.admin.models.dto.member.OutMemberDto;

import java.util.List;

public interface CommentDaoSub {

    /*****************************************************
     * Select
     ****************************************************/
    /**
     * 댓글 idx 가져오기
     *
     * @return
     */
    List<Long> getCommentContentsIdxList(String memberUuid);

}
