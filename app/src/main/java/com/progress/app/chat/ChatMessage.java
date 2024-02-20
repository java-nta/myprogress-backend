package com.progress.app.chat;



import java.util.Date;
import java.util.UUID;

import com.progress.app.utils.TimeFormater;

import lombok.Data;

@Data
public class ChatMessage {
  private UUID id;
  private String content;
  private String sender;
  private String timeStamp;
  
  public ChatMessage(String content, String sender) {
    this.id = UUID.randomUUID();
    this.content = content;
    this.sender = sender;
    this.timeStamp = TimeFormater.formateTime(new Date());
  }
}
