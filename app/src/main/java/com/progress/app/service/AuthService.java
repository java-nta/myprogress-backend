package com.progress.app.service;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.progress.app.model.UserDetailsImpl;
import com.progress.app.provider.JwtTokenProvider;

@Service
public class AuthService {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  public static UserDetailsImpl getAuthenticatedUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()) {
      throw new AuthenticationCredentialsNotFoundException("User not authenticated");
    }
    return (UserDetailsImpl) auth.getPrincipal();
  }

  public String auth(String username, String password) throws AuthenticationException {
    try {
      Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

      Authentication authenticated = authenticationManager.authenticate(authentication);

      SecurityContextHolder.getContext().setAuthentication(authenticated);
      return jwtTokenProvider.generateJwtToken(username);
    } catch (BadCredentialsException e) {
      throw new AuthenticationException("Invalid username or password", e);
    } catch (Exception e) {
      throw new AuthenticationException("Authentication failed", e);
    }
  }

}
