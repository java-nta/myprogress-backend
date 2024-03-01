package com.progress.app.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "post_comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private Long id;

  @Column(name = "comment")
  private String comment;

  @Column(name = "commented_by")
  private String by;

  @ManyToOne
  @JoinColumn(name = "post_id", referencedColumnName = "id")
  private Post post;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @Column(name = "created_at")
  private Date createdAt;

  @Column(name = "updated_at")
  private Date updatedAt;

}
