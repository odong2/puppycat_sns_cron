package com.architecture.admin.models.dao.search;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SearchLogDao {

    /*****************************************************
     * Delete
     ****************************************************/
    /**
     * 검색 로그 삭제
     *
     * @param date 삭제 기준 날짜
     */
    void deleteSearchLog(String date);

}
