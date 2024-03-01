package com.progress.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.progress.app.dto.PostDTO;
import com.progress.app.model.Comment;
import com.progress.app.model.Post;
import com.progress.app.model.User;
import com.progress.app.model.UserDetailsImpl;
import com.progress.app.repository.CommentRepository;
import com.progress.app.repository.PostRepository;
import com.progress.app.repository.UserRepository;
import com.progress.app.utils.TimeFormater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

  private final PostRepository postRepository;

  private final CommentRepository commentRepository;

  private final UserRepository userRepository;

  private PostDTO mapToPostDTO(Post p) {
    return PostDTO.builder()
        .id(p.getId())
        .author(p.getAuthor())
        .images(p.getImages())
        .createdAt(TimeFormater.formateTime(p.getCreatedAt()))
        .build();
  }

  public List<PostDTO> serveAllPost() {
    List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
    return posts.stream().map(this::mapToPostDTO).toList();
  }

  public void serveAddCommentToPost(String comment, Post post) {
    UserDetailsImpl userImp = AuthService.getAuthenticatedUser();
    User user = userRepository.findByUsername(userImp.getUsername()).get();
    Comment newComment = Comment.builder()
        .user(user)
        .comment(comment)
        .post(post)
        .build();
    if (newComment != null) {
      commentRepository.save(newComment);
      log.info("COMMENT ADDED id: {}", newComment.getId());
    }
  }
}
