package com.progress.app.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private Long id;

  @Column(name = "author")
  private String author;

  @Column(name = "text")
  private String text;

  @Column(name = "like_nbr")
  private Long likeNbr;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
  private List<Comment> comments;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
  private List<PostImage> images;

  @Column(name = "created_at")
  private Date createdAt;

  @Column(name = "updated_at")
  private Date updatedAt;

}
