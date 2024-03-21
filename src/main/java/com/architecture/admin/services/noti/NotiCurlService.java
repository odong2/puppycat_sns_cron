package com.architecture.admin.services.noti;

import com.architecture.admin.models.dto.noti.NotiDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "notiCurlService", url = "${member.domain}/v1/noti")
public interface NotiCurlService {

    /*****************************************************
     *  SubFunction - select
     ****************************************************/
    @PostMapping("/follow/list")
    String notifyFollowerOfNewContents(@RequestBody NotiDto notiDto);

    /*****************************************************
     *  SubFunction - Insert
     ****************************************************/


    /*****************************************************
     *  SubFunction - Update
     ****************************************************/


}
