package com.example.chatx_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.example.chatx_api",
		"com.example.auth_common" //공통 인증 모듈 (인터셉터, 리졸버, webConfig)
	}
)
public class ChatxApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatxApiApplication.class, args);
	}

}
