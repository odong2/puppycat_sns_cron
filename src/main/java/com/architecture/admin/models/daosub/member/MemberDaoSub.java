package com.architecture.admin.models.daosub.member;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface MemberDaoSub {

    /*****************************************************
     * Select
     ****************************************************/
    /**
     * 인기 유저 리스트
     *
     * @return 인기 유저 100명
     */
    List<Long> getPopularMemberList();
}
