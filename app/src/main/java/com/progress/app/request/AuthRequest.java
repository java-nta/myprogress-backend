package com.progress.app.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {

  @NotBlank
  String username;

  @NotBlank
  String password;
}
