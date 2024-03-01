package com.progress.app.request;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddUserRequest {
  @NotBlank(message = "Username is empty")
  @Size(min = 4, max = 15)
  @NonNull
  private String username;

  @NotBlank(message = "Email is empty")
  @Email(message = "Email not valid")
  @NonNull
  private String email;

  @NotBlank(message = "Password is empty")
  @Size(min = 4, max = 15)
  @NonNull
  private String password;
}
