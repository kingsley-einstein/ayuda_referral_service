package com.ayuda.referral.services.auth;

import com.ayuda.referral.server.ServiceResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service", path = "/api/v1", url = "${auth.url}")
public interface AuthClient {
  @GetMapping("/auth/getloggeduser")
  public ServiceResponse<AuthModel> getAuthModel(@RequestHeader("Authorization") String authorization);
}
