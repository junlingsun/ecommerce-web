package com.junling.online_mall.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com/junling/online_mall/product/feign")
public class OnlineMallProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineMallProductApplication.class, args);
	}

}
