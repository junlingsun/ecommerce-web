package com.junling.online_mall.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com/junling/online_mall/member/dao")
public class OnlineMallMemberApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineMallMemberApplication.class, args);
	}

}
