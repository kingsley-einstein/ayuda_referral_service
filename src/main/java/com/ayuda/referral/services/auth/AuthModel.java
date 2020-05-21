package com.ayuda.referral.services.auth;

import java.util.UUID;

@SuppressWarnings("serial")
public class AuthModel implements java.io.Serializable {
  private UUID id;
  private String email;
  private String firstName;
  private String lastName;

  public AuthModel() {}

  public UUID getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }
}