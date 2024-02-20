package com.progress.app.exception;

public class EmailAlreadyUsedException extends Exception{
  public EmailAlreadyUsedException(String message){
    super(message);
  }
}
