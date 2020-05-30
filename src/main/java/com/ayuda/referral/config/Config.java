package com.ayuda.referral.config;

import com.ayuda.referral.controllers.ReferralController;
// import com.ayuda.referral.services.auth.AuthClient;

import feign.codec.ErrorDecoder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
  @Bean
  public ReferralController referralController() {
    return new ReferralController();
  }

  @Bean
  public ErrorDecoder decoder() {
    return new FeignErrorConfig();
  }
}