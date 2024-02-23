package com.progress.app.dto;

import com.progress.app.model.User;

import lombok.Data;

@Data
public class UserDto {
  private Long id;
  private String username;
  private String email;
  private String role;
  private Boolean active;
  private String createdAt;
  private String updatedAt;

  // Constructor
  public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.active = user.getActive();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
  
}
