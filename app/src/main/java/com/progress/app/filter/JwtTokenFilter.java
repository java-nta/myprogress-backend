package com.progress.app.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progress.app.exception.ExceptionPayload;
import com.progress.app.exception.InvalidTokenException;
import com.progress.app.model.UserDetailsImpl;
import com.progress.app.provider.JwtTokenProvider;
import com.progress.app.service.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

  private JwtTokenProvider jwtTokenProvider;
  private UserDetailsServiceImpl userDetailsService;
  private final List<String> permitUrl = Arrays.asList("/auth/login", "/ws/chat");

  public JwtTokenFilter(JwtTokenProvider jwtTokenProvider, UserDetailsServiceImpl userDetailsServiceImpl) {
    this.jwtTokenProvider = jwtTokenProvider;
    this.userDetailsService = userDetailsServiceImpl;
  }

  @SuppressWarnings("null")
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      if (isPermitUrl(request)) {
        filterChain.doFilter(request, response);
        return;
      }

      String token = jwtTokenProvider.extractTokenFromRequest(request);
      log.info("FILTER START IN PATH {} FOR TOKEN {}", request.getRequestURI(), token);
      if (token != null && jwtTokenProvider.validateToken(token)) {
        String username = jwtTokenProvider.extractPayload(token);
        UserDetailsImpl user = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null,
            user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
      }
    } catch (InvalidTokenException e) {
      sendErrorResponse(response, e);
      return;
    }
  }

  public boolean isPermitUrl(HttpServletRequest request) {
    String url = request.getRequestURI();
    for (String elt : permitUrl) {
      if (url.startsWith(elt))
        return true;
    }
    return false;
  }

  private void sendErrorResponse(HttpServletResponse response, InvalidTokenException e) throws IOException {
    ExceptionPayload exceptionPayload = new ExceptionPayload(e.getMessage(), HttpStatus.UNAUTHORIZED.value(), null);
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write(new ObjectMapper().writeValueAsString(exceptionPayload));
  }
}
