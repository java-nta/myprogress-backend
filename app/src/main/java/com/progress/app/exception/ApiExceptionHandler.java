package com.progress.app.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.sasl.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;


@RestControllerAdvice
public class ApiExceptionHandler {

  private final int BAD_REQUEST = HttpStatus.BAD_REQUEST.value();


  @ExceptionHandler({ EmailAlreadyUsedException.class, AuthenticationException.class, 
      HttpRequestMethodNotSupportedException.class })
  public ResponseEntity<ExceptionPayload> handleException(Exception e) {
    String message = e.getMessage();
    ExceptionPayload exceptionPayload = new ExceptionPayload(message, BAD_REQUEST, null);
    return new ResponseEntity<>(exceptionPayload, HttpStatus.BAD_REQUEST);
  }



}
