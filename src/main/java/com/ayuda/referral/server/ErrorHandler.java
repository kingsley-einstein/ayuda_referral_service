package com.ayuda.referral.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {
  @ExceptionHandler(value = Error.class)
  public ResponseEntity<ServiceResponse<String>> exception(Error e) {
    return new ResponseEntity<>(
      new ServiceResponse<String>(
        e.getMessage(),
        e.getCode()
      ),
      HttpStatus.valueOf(
        e.getCode()
      )
    );
  }
}