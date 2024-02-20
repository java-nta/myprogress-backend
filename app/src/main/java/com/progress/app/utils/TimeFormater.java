package com.progress.app.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormater {
  private static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
  public static String formateTime(Date time){
    SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
    return sdf.format(time);
  }
}
