package com.architecture.admin.models.dto.contents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContentsDto {
    private Long idx;           // idx
    private Long rank;          // 랭킹
    private Long commentScore;  // 댓글 점수
    private Long likeScore;     // 좋아요 점수

}
