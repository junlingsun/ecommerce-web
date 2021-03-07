package com.junling.online_mall.thirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class OnlineMallThirdPartyApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineMallThirdPartyApplication.class, args);
	}

}
