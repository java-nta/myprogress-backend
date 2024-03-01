package com.progress.app.controller;

import java.util.Date;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.progress.app.request.AddUserRequest;
import com.progress.app.request.AuthRequest;
import com.progress.app.response.JwtTokenResponse;

import com.progress.app.service.AuthService;
import com.progress.app.service.UserService;
import com.progress.app.utils.TimeFormater;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired
  private AuthService authService;

  @Autowired
  private UserService userService;

  @PostMapping("/login")
  public ResponseEntity<Object> login(@RequestBody @Valid AuthRequest data)
      throws AuthenticationException {
    String token = authService.auth(data.getUsername(), data.getPassword());
    JwtTokenResponse response = new JwtTokenResponse("Happy fetching", data.getUsername(), token,
        TimeFormater.formateTime(new Date()));
    return ResponseEntity
        .ok(response);
  }

  @PostMapping("/signup")
  @ResponseStatus(code = HttpStatus.CREATED)
  public void login(@RequestBody @Valid AddUserRequest data) throws Exception {
    userService.serveAdduser(data.getUsername(), data.getEmail(), data.getPassword());

  }

  /**
   * this method will use the JwtTokenFilter to test the token
   */
  @GetMapping("/testToken")
  public ResponseEntity<String> authByJwtToken() {
    return ResponseEntity.ok("Happy Fitching");
  }
}
