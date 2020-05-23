package com.ayuda.referral.router;

import com.ayuda.referral.controllers.ReferralController;
import com.ayuda.referral.db.models.Referral;
import com.ayuda.referral.server.Error;
import com.ayuda.referral.server.ServiceResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class Router {
  @Autowired
  private ReferralController referralController;

  @PostMapping("/create")
  public ResponseEntity<ServiceResponse<Referral>> createReferral(
    @RequestHeader("Authorization") String authorization,
    @RequestBody Referral body
  ) {
    try {
      return referralController.createReferral(authorization, body);
    } catch (Error e) {
      throw new Error(e.getCode(), e.getMessage());
    }
  }
}