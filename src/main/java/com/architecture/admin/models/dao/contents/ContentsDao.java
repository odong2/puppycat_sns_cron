package com.architecture.admin.models.dao.contents;

import com.architecture.admin.models.dto.contents.ContentsDto;

import java.util.List;

public interface ContentsDao {

    /*****************************************************
     * Insert
     ****************************************************/
    /**
     * 인기 게시물 등록
     *
     * @param contentsList contents.idx, rank
     */
    void insertWeekPopularList(List<ContentsDto> contentsList);

    /**
     * 급상승 인기 게시물 등록
     *
     * @param contentsList contents.idx, rank
     */
    void insertHourPopularList(List<ContentsDto> contentsList);

    /**
     * 컨텐츠 삭제 상태값 수정
     *
     * @param String idx state
     */
    void updateDelContentsState(String memberUuid);

    /*****************************************************
     * Delete
     ****************************************************/
    /**
     * 인기 게시물 삭제
     */
    void deleteWeekPopularContents();

    /**
     * 급상승 인기 게시물 삭제
     */
    void deleteHourPopularContents();


}
