package com.progress.app.config;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.progress.app.exception.ExceptionPayload;

import org.springframework.http.MediaType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    ExceptionPayload exceptionResponse = new ExceptionPayload(
        "Username or password incorrect",
        401,
        null);
    response.setStatus(401);
    response.getWriter().write(new ObjectMapper().writeValueAsString(exceptionResponse));
    response.getWriter().flush();

  }

}
