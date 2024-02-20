package com.progress.app.response;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenResponse {
  private String message;
  private String username;
  private String token;
  private String issueAt;
}
