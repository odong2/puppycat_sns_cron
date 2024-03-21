package com.architecture.admin.services.search;

import com.architecture.admin.models.dao.search.SearchLogDao;
import com.architecture.admin.services.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "use.sqs.listener.enabled", havingValue = "true")
public class DelSearchLogService  extends BaseService {
    private final SearchLogDao searchLogDao;
    @Value("${search.log.del.date}")
    private Integer searchLogDelDate;       // 제거 할 기간

    /*****************************************************
     *  Scheduler
     ****************************************************/
    /**
     * (하루 4번 실행, 00:00, 06:00, 12:00, 18:00)
     */
    @Scheduled(cron = "0 0 0/6 * * *")
    public void delNoti() {

        // cron 활성화 상태 체크
        if (super.checkCronState(3)) {

            // 날짜 구하기 (UTC)
            SimpleDateFormat formatDatetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -searchLogDelDate);
            String beforeMonth = formatDatetime.format(calendar.getTime());
            String date = dateLibrary.localTimeToUtc(beforeMonth);

            // notiDelDate 전 데이터 지우기
            deleteSearchLogDel(date);
        }
    }

    /**
     * 회원 알림 삭제
     *
     * @param date 삭제 체크 날짜
     */
    public void deleteSearchLogDel(String date) {
        searchLogDao.deleteSearchLog(date);
    }

}
