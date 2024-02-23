package com.progress.app.model;

import java.util.Date;

import com.progress.app.utils.TimeFormater;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "posts")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Getter
  @Setter
  @Column(name = "text")
  private String text;

  @Getter
  @Setter
  @Column(name = "like_nbr")
  private Long likeNbr;

  @Column(name = "created_at")
  private Date createdAt;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public String getCreatedAt() {
    return TimeFormater.formateTime(createdAt);
  }

  @Column(name = "updated_at")
  private Date updatedAt;

  public String getUpdatedAt() {
    return TimeFormater.formateTime(updatedAt);
  }

  public Post() {
  }

  public Post(String text, User user) {
    this.text = text;
    this.likeNbr = 0L;
    this.user = user;
    this.createdAt = new Date();
    this.updatedAt = new Date();
  }

}
