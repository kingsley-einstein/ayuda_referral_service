package com.ayuda.referral;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AyudaReferralServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AyudaReferralServiceApplication.class, args);
	}

}
