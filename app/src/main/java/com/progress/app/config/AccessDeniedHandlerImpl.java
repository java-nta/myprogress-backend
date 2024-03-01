package com.progress.app.config;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progress.app.exception.ExceptionPayload;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    ExceptionPayload exceptionResponse = new ExceptionPayload(
        "You don't have the access to this action",
        HttpServletResponse.SC_FORBIDDEN,
        null);
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.getWriter().write(new ObjectMapper().writeValueAsString(exceptionResponse));
    response.getWriter().flush();
  }

}
