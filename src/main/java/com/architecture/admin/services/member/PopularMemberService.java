package com.architecture.admin.services.member;

import com.architecture.admin.models.dao.member.MemberDao;
import com.architecture.admin.services.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "use.sqs.listener.enabled", havingValue = "true")
public class PopularMemberService extends BaseService {

    private final MemberDao memberDao;

    /**
     * 인기 유저 등록 스케줄러
     * 매 시각 10분 시작 [ 1시간 주기 ]
     */
    @Scheduled(cron = "0 10 0/1 * * *")
    @Transactional
    public void registerPopularUser() {
        // 크론 체크
        if (super.checkCronState(6)) {
            // 기존 인기 유저 리스트 삭제
            memberDao.deletePopularMember();

            // 인기 유저 조회 [20230725 기준 조건 : 팔로워 수가 많고 콘텐츠가 하나 이상인 유저]
            List<Long> popularMemberList = memberDaoSub.getPopularMemberList();

            if (!ObjectUtils.isEmpty(popularMemberList)) {

                // 인기 유저 등록
                memberDao.insertPopularMemberList(popularMemberList);
            }

        }
    }

}
