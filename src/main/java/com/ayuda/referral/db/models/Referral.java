package com.ayuda.referral.db.models;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Table(name = "referrals")
@Entity(name = "referral")
public class Referral implements java.io.Serializable {
  @Id
  private UUID id;

  @NotNull(message = "Owner field is required.")
  @Column(name = "owner", nullable = false, unique = true)
  private UUID owner;

  @Column(name = "referredBy", nullable = true)
  private UUID referredBy;

  @NotNull(message = "Amount type is required")
  @Column(name = "amountType", nullable = false)
  private Integer amountType;
  
  public Referral() {}

  public Referral(UUID id, UUID owner, UUID referredBy, Integer amountType) {
    this.id = id;
    this.owner = owner;
    this.referredBy = referredBy;
    this.amountType = amountType;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getId() {
    return id;
  }

  public UUID getOwner() {
    return owner;
  }

  public UUID getReferredBy() {
    return referredBy;
  }

  public void setAmountType(Integer amountType) {
    this.amountType = amountType;
  }

  public Integer getAmountType() {
    return amountType;
  }

  public void setReferredBy(UUID referredBy) {
    this.referredBy = referredBy;
  }
}