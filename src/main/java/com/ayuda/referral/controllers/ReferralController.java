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
    List<Referral> refs = null;
    if (
      body.getReferredBy() != null || 
      body.getReferredBy().toString().trim().length() > 0
    ) {
      refs = repository.findByReferredBy(body.getReferredBy());
    } else {
      if (body.getAmountType() > 100000 || body.getAmountType() < 500) {
        throw new Error(
          400, 
          "You can't select amount type less than 500 or greater than 100000."
        );
      }
      if(!amountTypeAsString.startsWith("5") && !amountTypeAsString.startsWith("2") && !amountTypeAsString.startsWith("1")) {
        throw new Error(
          400,
          "Invalid amount type."
        );
      }
    }

    if (refs != null && refs.size() == 10) {
      throw new Error(
        400,
        "Your referrer already has maximum number of referrals."
      );
    }

    Referral referrer = null;

    if (body.getReferredBy() != null) {
      referrer = repository.findById(body.getReferredBy()).orElseThrow(() -> {
        return new Error(
          404,
          "Referrer not found."
        );
      });
    }

    Referral r1 = null;

    if (referrer != null) {
      r1 = new Referral(UUID.randomUUID(), authModel.getId(), body.getReferredBy(), referrer.getAmountType());
    } else {
      r1 = new Referral(UUID.randomUUID(), authModel.getId(), null, body.getAmountType());
    }

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

 public ResponseEntity<ServiceResponse<Referral>> getByOwner(String authorization) {
   try {
     ServiceResponse<AuthModel> authResponse = authClient.getAuthModel(authorization);
     AuthModel authModel = authResponse.getResponse();
     Referral r = repository.findByOwner(authModel.getId()).orElseThrow(() -> {
       return new Error(
         404,
         "Referral object not found"
       );
     });
     return new ResponseEntity<>(
       new ServiceResponse<Referral>(
         r, 200
       ),
       HttpStatus.OK
     );
   } catch (Error e) {
     throw new Error(e.getCode(), e.getMessage());
   }
 }

 public ResponseEntity<ServiceResponse<List<Referral>>> getAllReferralList(UUID referredBy) {
   try {
     List<Referral> referrals = repository.findByReferredBy(referredBy);
     return new ResponseEntity<>(
       new ServiceResponse<List<Referral>>(
         referrals, 200
       ),
       HttpStatus.OK
     );
   } catch (Exception e) {
     throw new Error(500, e.getMessage());
   }
 }

 public ResponseEntity<ServiceResponse<List<Referral>>> getAllReferralsReferredBy(
   UUID referredBy, 
   Integer page
   ) {
   try {
    Page<Referral> referralPage = repository.findByReferredBy(referredBy, PageRequest.of(page, 5));
    List<Referral> referralList = referralPage.getContent();
    ServiceResponse<List<Referral>> response = new ServiceResponse<List<Referral>>(referralList, 200);
    return new ResponseEntity<>(
      response, HttpStatus.OK
    );
   } catch (Exception e) {
     throw new Error(500, e.getMessage());
   }
 }

 public ResponseEntity<ServiceResponse<Long>> countByReferredBy(UUID referredBy) {
   try {
     Long count = repository.countByReferredBy(referredBy);
     return new ResponseEntity<>(
       new ServiceResponse<Long>(
         count, 200
       ),
       HttpStatus.OK
     );
   } catch (Exception e) {
     throw new Error(500, e.getMessage());
   }
 }

 public ResponseEntity<ServiceResponse<String>> withDraw(String authorization) {
   try {
    ServiceResponse<AuthModel> authResponse = authClient.getAuthModel(authorization);
    AuthModel authModel = authResponse.getResponse();
    Referral r1 = repository.findByOwner(authModel.getId()).orElseThrow(() -> {
      return new Error(
        404,
        "Referral object not found"
      );
    });
    List<Referral> refs = repository.findByReferredBy(r1.getId());
    
    refs.forEach((ref) -> {
      ref.setReferredBy(null);
      repository.save(ref);
    });
    
    r1.setId(UUID.randomUUID());
    Referral r2 = repository.save(r1);
    String s = String.format(
      "Successfully withdrawn - Referral code set to %s",
      r2.getId().toString()
    );
    return new ResponseEntity<>(
      new ServiceResponse<String>(s, 200),
      HttpStatus.OK
    );
   } catch (Error e) {
     throw new Error(e.getCode(), e.getMessage());
   }
 }
}