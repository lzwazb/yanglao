package com.administrator.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("activity-server")
public interface ActivityServerClient {

    @GetMapping("/activity/count")
    Long getActivityCount();
}
