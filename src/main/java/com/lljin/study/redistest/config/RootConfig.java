package com.lljin.study.redistest.config;

import com.lljin.study.redistest.feign.TestFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author lljin
 * @description TODO
 * @date 2020/8/2 22:08
 */
@EnableFeignClients(clients = TestFeignClient.class)
@Configuration
public class RootConfig {
}
