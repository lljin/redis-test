package com.lljin.study.redistest.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("user-service")
public interface TestFeignClient {

    @GetMapping("/test")
    String test();
}
