package com.ayuda.referral.config;

import com.ayuda.referral.controllers.ReferralController;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
  @Bean
  public ReferralController referralController() {
    return new ReferralController();
  }
}