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
@Table(name = "post_imgs")
public class PostImage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  private Long id;

  @Getter
  @Setter
  @Column(name = "img_path")
  private String path;

  @Column(name = "created_at")
  private Date createdAt;

  @ManyToOne
  @JoinColumn(name = "post_id", referencedColumnName = "id")
  private Post post;

  public String getCreatedAt() {
    return TimeFormater.formateTime(createdAt);
  }

  public PostImage() {
  }

  public PostImage(String path, Post post) {
    this.path = path;
    this.post = post;
    this.createdAt = new Date();
  }

}
