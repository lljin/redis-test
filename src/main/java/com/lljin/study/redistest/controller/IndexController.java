package com.lljin.study.redistest.controller;

import com.lljin.study.redistest.feign.TestFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lljin
 * @description TODO
 * @date 2020/8/2 22:17
 */
@RestController
public class IndexController {
    private final TestFeignClient testFeignClient;

    public IndexController(TestFeignClient testFeignClient) {
        this.testFeignClient = testFeignClient;
    }

    @GetMapping("/test")
    public String test(){
        return testFeignClient.test();
    }
}
