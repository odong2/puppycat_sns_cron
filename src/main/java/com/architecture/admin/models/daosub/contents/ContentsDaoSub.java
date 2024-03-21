package com.architecture.admin.models.daosub.contents;

import com.architecture.admin.models.dto.SearchDto;
import com.architecture.admin.models.dto.contents.ContentsDto;

import java.util.List;

public interface ContentsDaoSub {

    /**
     * 인기 게시물 리스트 조회 [ 인기 게시물 & 급상승 인기 게시물 ]
     *
     * @param searchDto date, nowDate
     * @return 인기 게시물 리스트
     */
    List<ContentsDto> getPopularList(SearchDto searchDto);
}
