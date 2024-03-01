package com.progress.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.progress.app.filter.JwtTokenFilter;
import com.progress.app.provider.JwtTokenProvider;
import com.progress.app.service.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final UserDetailsServiceImpl userDetailsService;

  private final AuthenticationEntryPointImpl authenticationEntryPoint;

  private final JwtTokenProvider jwtTokenProvider;

  private final AccessDeniedHandlerImpl accessDeniedHandler;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(c -> c.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/auth/**").permitAll()
            .requestMatchers("/post/imgs/**").permitAll()
            .requestMatchers("/ws/**").permitAll()
            .requestMatchers("/user/**").hasRole("ADMIN")
            .anyRequest().permitAll())
        .userDetailsService(userDetailsService)
        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling(
            ex -> ex.authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler))
        .addFilterBefore(new JwtTokenFilter(jwtTokenProvider, userDetailsService),
            UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
