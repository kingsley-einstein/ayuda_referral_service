package com.ayuda.referral.router;

import java.util.List;
import java.util.UUID;

import com.ayuda.referral.controllers.ReferralController;
import com.ayuda.referral.db.models.Referral;
import com.ayuda.referral.server.Error;
import com.ayuda.referral.server.ServiceResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @GetMapping("/owned")
  public ResponseEntity<ServiceResponse<Referral>> getReferral(@RequestHeader("Authorization") String authorization) {
    try {
      return referralController.getByOwner(authorization);
    } catch (Error e) {
      throw new Error(e.getCode(), e.getMessage());
    }
  }

  @GetMapping("/referred/{referredBy}/{page}")
  public ResponseEntity<ServiceResponse<List<Referral>>> getAllReferrals(
    @PathVariable("referredBy") UUID referredBy,
    @PathVariable("page") Integer page
  ) {
    try {
      return referralController.getAllReferralsReferredBy(referredBy, page);
    } catch (Error e) {
      throw new Error(e.getCode(), e.getMessage());
    }
  }
}