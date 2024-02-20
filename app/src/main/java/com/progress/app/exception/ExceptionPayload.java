package com.progress.app.exception;

import java.util.Date;
import java.util.HashMap;

import com.progress.app.utils.TimeFormater;

import lombok.Data;

@Data
public class ExceptionPayload {
  private String message;
  private int status;
  private HashMap<String, Object> errors;
  private String timeStamp;

  public ExceptionPayload(String message, int status, HashMap<String, Object> errors) {
    this.message = message;
    this.status = status;
    this.errors = errors;
    this.timeStamp = TimeFormater.formateTime(new Date());
  }

}