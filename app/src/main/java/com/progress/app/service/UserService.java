package com.progress.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.progress.app.exception.EmailAlreadyUsedException;
import com.progress.app.model.Post;
import com.progress.app.model.User;
import com.progress.app.repository.PostRepository;
import com.progress.app.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private String encodePassword(String password) {
    return passwordEncoder.encode(password);
  }

  public List<User> serveAllUsers() {
    return userRepository.findAll();
  }

  public User serveAdduser(String username, String email, String password) throws Exception {
    if (userRepository.existsByEmail(email)) {
      throw new EmailAlreadyUsedException("email already used");
    }
    String encoderPassword = encodePassword(password);
    log.info("encode: {}", encoderPassword);
    return userRepository.save(new User(null, username, email, encoderPassword, null));
  }

  public User serveFindUserById(@NonNull Long id) {
    Optional<User> user = userRepository.findById(id);
    if(!user.isPresent()){
      throw new EntityNotFoundException("User not found");
    }
    return userRepository.findById(id).get();
  }

  public void serveDeleteUserById(@NonNull Long id) {
    userRepository.deleteById(id);
  }

  public void serveDeleteUsersById(@NonNull List<Long> ids) {
    userRepository.deleteAllById(ids);
    ;
  }

  public void serveAddPostForAuthenticatedUser(String text) {

    String username = AuthService.getAuthenticatedUser().getUsername();
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));

    Post newPost = new Post(text, user);
    postRepository.save(newPost);
  }

  public List<Post> getPostsForAuthenticatedUser() {

    String username = AuthService.getAuthenticatedUser().getUsername();
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));

    return user.getPosts();

  }
}
