package com.architecture.admin.services.contents;

import com.architecture.admin.models.dao.contents.ContentsDao;
import com.architecture.admin.models.daosub.contents.ContentsDaoSub;
import com.architecture.admin.models.dto.SearchDto;
import com.architecture.admin.models.dto.contents.ContentsDto;
import com.architecture.admin.services.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "use.sqs.listener.enabled", havingValue = "true")
public class ContentsService extends BaseService {

    private final ContentsDao contentsDao;
    private final ContentsDaoSub contentsDaoSub;

    /**
     * 인기 게시물 등록 스케줄러
     * 매 시각 정각 시작. [ 15분 주기 ]
     */
    @Scheduled(cron = "15 * * * * *")
    @Transactional
    public void registerWeekPopularContents() {
        // 크론 체크
        if (super.checkCronState(4)) {
            // 기존 인기 게시물 리스트 삭제
            contentsDao.deleteWeekPopularContents();

            SearchDto searchDto = new SearchDto();
            searchDto.setDate(dateLibrary.getAgoWeek(7));
            searchDto.setNowDate(dateLibrary.getDatetime());
            // 인기 게시물 조회
            List<ContentsDto> contentsList = contentsDaoSub.getPopularList(searchDto);

            if (contentsList != null && !contentsList.isEmpty()) {
                long rank = 1;

                for (ContentsDto contentsDto : contentsList) {
                    contentsDto.setRank(rank++); // 랭킹 set
                }
                // 인기 게시물 등록
                contentsDao.insertWeekPopularList(contentsList);
            }

        }
    }

    /**
     * 급상승 인기 게시물 등록 스케줄러
     * 정각 기준 5분부터 시작해 [ 15분 주기 ]
     *
     */
    @Scheduled(cron = "0 5/15 * * * *")
    @Transactional
    public void registerHourPopularContents() {
        // 크론 체크
        if (super.checkCronState(5)) {
            // 기존 인기 게시물 리스트 삭제
            contentsDao.deleteHourPopularContents();

            SearchDto searchDto = new SearchDto();
            searchDto.setDate(dateLibrary.getAgoHour(1));
            searchDto.setNowDate(dateLibrary.getDatetime());

            // 급상승 인기 게시물 조회
            List<ContentsDto> contentsList = contentsDaoSub.getPopularList(searchDto);

            if (contentsList != null && !contentsList.isEmpty()) {
                long rank = 1;

                for (ContentsDto contentsDto : contentsList) {
                    contentsDto.setRank(rank++); // 랭킹 set
                }
                // 인기 게시물 등록
                contentsDao.insertHourPopularList(contentsList);
            }

        }
    }

    /**
     * 컨텐츠 삭제 상태값 0 변경
     *
     */
    public void updateDelContentsState(String memberUuid){

        contentsDao.updateDelContentsState(memberUuid);
    }

}
