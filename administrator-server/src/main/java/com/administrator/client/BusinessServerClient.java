package com.administrator.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("business-server")
public interface BusinessServerClient {

    @GetMapping("/service/order/count")
    Long getServiceOrderCount();
}
