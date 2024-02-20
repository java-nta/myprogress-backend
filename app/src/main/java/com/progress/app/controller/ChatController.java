package com.progress.app.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.progress.app.chat.ChatMessage;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ChatController {

  @MessageMapping("/public")
  @SendTo("/topic/public")
  public ChatMessage brodcast(@Payload ChatMessage message) {
    log.info("MESSAGE RECEIVED: {}", message.getContent());
    return message;
  }
}
