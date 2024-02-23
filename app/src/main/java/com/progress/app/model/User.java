package com.progress.app.model;

import java.util.Date;
import java.util.List;

import com.progress.app.utils.TimeFormater;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  private Long id;

  @Setter
  @Getter
  @Column(name = "username")
  private String username;

  @Setter
  @Getter
  @Column(name = "email")
  private String email;

  @Setter
  @Getter
  @Column(name = "password")
  private String password;

  @Setter
  @Getter
  @Column(name = "role")
  private String role;

  @Setter
  @Getter
  @Column(name = "active")
  private Boolean active;


  @Getter
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Post> posts;

  @Column(name = "created_at")
  private Date createdAt;

  public String getCreatedAt() {
    return TimeFormater.formateTime(createdAt);
  }

  @Column(name = "updated_at")
  private Date updatedAt;

  public String getUpdatedAt() {
    return TimeFormater.formateTime(updatedAt);
  }

  // Default constructor
  public User() {

  }

  public User(Long id, String username, String email, String password, String role) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.active = true;
    this.createdAt = new Date();
    this.updatedAt = new Date();
    if (role == null) {
      this.role = "ROLE_USER";
    }
  }
}
