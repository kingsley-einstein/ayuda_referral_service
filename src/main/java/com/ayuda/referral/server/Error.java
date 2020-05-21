package com.ayuda.referral.server;

@SuppressWarnings("serial")
public class Error extends RuntimeException {
  private Integer code;

  public Error(Integer code, String message) {
    super(message);
    this.code = code;
  }

  public Integer getCode() {
    return code;
  }
}