package com.architecture.admin.models.dao.member;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MemberDao {

    /*****************************************************
     * Insert
     ****************************************************/
    /**
     * 인기 유저 등록
     * @param list 인기 유저 memberIdx 리스트
     */
    void insertPopularMemberList(List<Long> list);

    /*****************************************************
     * Delete
     ****************************************************/
    /**
     * 인기 유저 삭제
     */
    void deletePopularMember();

}
