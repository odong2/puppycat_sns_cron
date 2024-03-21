package com.architecture.admin.models.dto.noti;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContentsNotiDto {

    // sns_contents_mention_mapping
    private Long idx;           // 고유 번호
    private Long contentsIdx;   // 컨텐츠 번호
    private Long mentionIdx;    // 멘션 번호
    private Integer state;      // 상태값
    private String regDate;     // 등록일
    private String regDateTz;   // 등록일 타임존

    // sns_member_mention
    private String memberUuid;

    // sns_img_member_tag_mapping
    private Long imgIdx;        // 컨텐츠 번호

    // sns_member_follow
    private Long followIdx;     // 팔로워idx

    private String contents;    // 컨텐츠 내용
    private String nick;        // 회원 닉네임
    private String url;         // 이미지 url
    private String startDate;   // 검색 시작일
    private String endDate;     // 검색 종료일

    // sql
    private Long insertedIdx;
    private Long affectedRow;

    // TODO: 추후 사라질 예정
    private Long memberIdx;    // 회원idx

}
