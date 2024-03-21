package com.architecture.admin.models.dto.member;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutMemberDto {
    /**
     * sns_member_out
     */
    private Long idx;   // sns_member_out.idx
    private Long memberIdx;       // sns_member.idx
    private String id;      // 회원 아이디
    private String uuid;    // 고유아이디
    private String password;      // 비밀번호
    private String nick;    // 회원 닉네임
    private String partner;     // 파트너명
    private String lang;        // 사용언어
    private Integer isSimple;   // 간편가입
    private String loginIp;     // 로그인 아이피
    private String joinIp;      // 가입 아이피
    private String lastLogin;   // 마지막 로그인(UTC)
    private String lastLoginTz; // 마지막 로그인 타임존
    private String regDate;     // 가입일
    private String regDateTz;   // 가입일 타임존

    private Integer codeIdx; // 탈퇴사유코드
    // 1 : 기록을 삭제하고 싶어요
    // 2 : 이용이 불편하고 장애가 많아요
    // 3 : 개인정보 유출이 우려돼요
    // 4 : 사용 빈도가 낮아요
    // 5 : 콘텐츠가 불만스러워요
    // 6 : 다른 사이트가 더 좋아요
    // 7 : 직접입력
    private String outRegDate;      // 탈퇴신청일
    private String outRegDateTz;    // 탈퇴신청일 타임존
    private String outDate;         // 탈퇴확정일
    private String outDateTz;       // 탈퇴확정일 타임존

    /**
     * sns_member_info
     **/
    private String name;        // 이름
    private String phone;       // 전화번호
    private String gender;      // 성별(M: male, F: female)
    private String birth;       // 생년월일

    /**
     * sns_member_simple
     **/
    private String simpleId;    // 간편가입 넘어오는 아이디
    private String simpleType;  // 간편가입 타입 (ex kakao google)
    private String authToken;   // 토큰 값 (refresh)

    /**
     * sns_member_out_reason
     */
    private String reason; // 탈퇴 상세사유

    // etc
    private List<Long> idxList; // idx 리스트
    private List<Long> contentsIdxList; // 콘텐츠 idx 리스트
    private List<Long> commentIdxList; // 댓글 idx 리스트

    // sql
    private Long insertedIdx;
}
