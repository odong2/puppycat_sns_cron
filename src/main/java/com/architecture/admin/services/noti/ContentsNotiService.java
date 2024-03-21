package com.architecture.admin.services.noti;

import com.architecture.admin.models.daosub.noti.ContentsNotiDaoSub;
import com.architecture.admin.models.dto.noti.ContentsNotiDto;
import com.architecture.admin.models.dto.noti.NotiDto;
import com.architecture.admin.services.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "use.sqs.listener.enabled", havingValue = "true")
public class ContentsNotiService extends BaseService {

    private final ContentsNotiDaoSub contentsNotiDaoSub;
    private final NotiCurlService notiCurlService;

    @SqsListener(value = "${cloud.aws.sqs.contents.noti.listener}", deletionPolicy = SqsMessageDeletionPolicy.NO_REDRIVE)
    @SneakyThrows
    public void contentsNoti(@Payload String message) {

        // 큐 data 꺼내오기
        JSONObject obj = new JSONObject(message);
        String action = obj.getString("action");
        long contentsIdx = obj.getLong("contents_idx");

        // DTO세팅
        NotiDto notiDto = new NotiDto();

        // 컨텐츠 작성 정보(member_uuid, contents, reg_date)
        ContentsNotiDto contentsWriterInfo = selectContentsWriterInfo(contentsIdx);

        String senderUuid = contentsWriterInfo.getMemberUuid();
        String writeDate = contentsWriterInfo.getRegDate();
        String contents = contentsWriterInfo.getContents();

        // 컨텐츠 이미지 첫번째꺼 가져오기
        String imgUrl = selectContentsImg(contentsIdx);

        // DTO 세팅
        notiDto.setSenderUuid(senderUuid);
        notiDto.setContents(contents);
        notiDto.setContentsIdx(contentsIdx);
        notiDto.setImg(imgUrl);
        notiDto.setType(1);
        notiDto.setSubType(action);
        notiDto.setTitle("새 글");
        notiDto.setBody("님이 새 게시물을 올렸습니다.");

        // 컨텐츠 등록시 팔로우에게 알림 등록
        if (Objects.equals(action, "new_contents")) {
            /*
                - 1순위 One :: 로그인 최근 1주일
                - 2순위 Two :: 로그인 최근 1주일~30일
                - 3순위 Thr :: 로그인 30일 이후
             */
            SimpleDateFormat formatDatetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();

            // 일주일 전 날짜 구하기
            calendar.add(Calendar.DATE, -7);
            String beforeWeek = formatDatetime.format(calendar.getTime());
            String lastWeek = dateLibrary.localTimeToUtc(beforeWeek);

            // 30일 전 날짜 구하기
            calendar.add(Calendar.DATE, -30);
            String beforeMonth = formatDatetime.format(calendar.getTime());
            String lastMonth = dateLibrary.localTimeToUtc(beforeMonth);

            // 조회용
            ContentsNotiDto contentsNotiDto = ContentsNotiDto.builder()
                    .memberUuid(senderUuid)  // 작업자 uuid
                    .regDate(writeDate)      // 글 작성 시간
                    .build();

            // 컨텐츠 작성자의 팔로워 회원 목록 가져오기 [글 작성 일자보다 전에 팔로한 회원]
            List<String> followerList = contentsNotiDaoSub.getFollowerMember(contentsNotiDto);

            if (!ObjectUtils.isEmpty(followerList)) {

                notiDto.setStartDate(lastMonth);         // 로그인 조건 (한달 전)
                notiDto.setEndDate(lastWeek);            // 로그인 조건 (일주일 전)
                notiDto.setMemberUuidList(followerList); // 알림 보낼 팔로워 set

                // 알림 등록
                notiCurlService.notifyFollowerOfNewContents(notiDto);
            }
        }
    }

    /*****************************************************
     *  SubFunction - select
     ****************************************************/

    /**
     * 컨텐츠 작성자 정보 가져오기
     *
     * @param contentsIdx 컨텐츠idx
     * @return memeberidx nick regdate
     */
    public ContentsNotiDto selectContentsWriterInfo(Long contentsIdx) {
        return contentsNotiDaoSub.getContentsWriterInfo(contentsIdx);
    }

    /**
     * 첫번째 이미지 가져오기
     *
     * @param contentsIdx 컨텐츠idx
     * @return img url
     */
    public String selectContentsImg(Long contentsIdx) {
        return contentsNotiDaoSub.getContentsImg(contentsIdx);
    }

}
