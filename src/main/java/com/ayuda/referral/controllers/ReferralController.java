package com.ayuda.referral.controllers;

// import java.util.Map;

import com.ayuda.referral.db.models.Referral;
import com.ayuda.referral.db.repositories.ReferralRepository;
import com.ayuda.referral.server.Error;
import com.ayuda.referral.server.ServiceResponse;
import com.ayuda.referral.services.auth.AuthClient;
import com.ayuda.referral.services.auth.AuthModel;

// import java.util.Map;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Component;

public class ReferralController {
 @Autowired
 private ReferralRepository repository;

 @Autowired
 private AuthClient authClient;

 public ResponseEntity<ServiceResponse<Referral>> createReferral(String authorization, Referral body) {
   try {
    ServiceResponse<AuthModel> authResponse = authClient.getAuthModel(authorization);
    AuthModel authModel = authResponse.getResponse();
    String amountTypeAsString = body.getAmountType().toString();
    List<Referral> refs = repository.findByReferredBy(body.getReferredBy());
    if (body.getAmountType() > 100000 || body.getAmountType() < 500) {
      throw new Error(
        400, 
        "You can't select amount type less than 500 or greater than 100000"
      );
    }
    if(!amountTypeAsString.startsWith("5") || !amountTypeAsString.startsWith("2") || !amountTypeAsString.startsWith("1")) {
      throw new Error(
        400,
        "Invalid amount type"
      );
    }
    if (refs.size() == 10) {
      throw new Error(
        400,
        "Your referrer already has maximum number of referrals."
      );
    }
    Referral r1 = new Referral(UUID.randomUUID(), authModel.getId(), body.getReferredBy(), body.getAmountType());
    Referral r2 = repository.save(r1);
    ServiceResponse<Referral> sr = new ServiceResponse<Referral>(
      r2, 201
    );
    return new ResponseEntity<>(
      sr, HttpStatus.CREATED
    );
   } catch (Error e) {
     throw new Error(e.getCode(), e.getMessage());
   }
 }

 public ResponseEntity<ServiceResponse<List<Referral>>> getAllReferralsReferredBy(
   UUID referredBy, 
   Integer page
   ) {
   try {
    Page<Referral> referralPage = repository.findByReferredBy(referredBy, PageRequest.of(page, 10));
    List<Referral> referralList = referralPage.getContent();
    ServiceResponse<List<Referral>> response = new ServiceResponse<List<Referral>>(referralList, 200);
    return new ResponseEntity<>(
      response, HttpStatus.OK
    );
   } catch (Error e) {
     throw new Error(e.getCode(), e.getMessage());
   }
 }
}