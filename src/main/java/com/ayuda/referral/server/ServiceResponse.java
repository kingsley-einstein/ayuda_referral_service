package com.ayuda.referral.server;

@SuppressWarnings("serial")
public class ServiceResponse<T> implements java.io.Serializable {
  private T response;
  private Integer code;

  public ServiceResponse() {}

  public ServiceResponse(T response, Integer code) {
    this.response = response;
    this.code = code;
  }

  public T getResponse() {
    return response;
  }

  public Integer getCode() {
    return code;
  }
}