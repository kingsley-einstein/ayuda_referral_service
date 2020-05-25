package com.ayuda.referral.db.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ayuda.referral.db.models.Referral;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReferralRepository extends JpaRepository<Referral, UUID> {
  // List<Referral> findByReferredBy(UUID referredBy);
  Page<Referral> findByReferredBy(UUID referredBy, Pageable pageable);
  List<Referral> findByReferredBy(UUID referredBy);
  Optional<Referral> findByOwner(UUID Owner);
  Long countByReferredBy(UUID referredBy);
}