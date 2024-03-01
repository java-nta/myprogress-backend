package com.progress.app.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.progress.app.dto.UserDTO;
import com.progress.app.exception.EmailAlreadyUsedException;
import com.progress.app.model.Post;
import com.progress.app.model.PostImage;
import com.progress.app.model.User;
import com.progress.app.repository.PostRepository;
import com.progress.app.repository.UserRepository;
import com.progress.app.utils.FileSaver;
import com.progress.app.utils.TimeFormater;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

  @Value("${upload.imgs}")
  private String uploadToPostImgs;

  private final UserRepository userRepository;

  private final PostRepository postRepository;

  private final PasswordEncoder passwordEncoder;

  private String encodePassword(String password) {
    return passwordEncoder.encode(password);
  }

  private UserDTO mapToUserDTO(User u) {
    return UserDTO.builder()
        .id(u.getId())
        .username(u.getUsername())
        .email(u.getEmail())
        .role(u.getRole())
        .active(u.getActive())
        .createdAt(TimeFormater.formateTime(u.getCreatedAt()))
        .updatedAt(TimeFormater.formateTime(u.getUpdatedAt()))
        .build();
  }

  public List<UserDTO> serveAllUsers() {
    List<User> users = userRepository.findAll();
    return users.stream().map(this::mapToUserDTO).toList();
  }

  @SuppressWarnings("null")
  public void serveAdduser(String username, String email, String password) throws Exception {
    if (userRepository.existsByEmail(email)) {
      throw new EmailAlreadyUsedException("email already used");
    }
    String encoderPassword = encodePassword(password);
    Date now = new Date();
    User newUser = User.builder()
        .username(username)
        .email(email)
        .password(encoderPassword)
        .active(true)
        .role("ROLE_ADMIN")
        .createdAt(now)
        .updatedAt(now)
        .build();
    userRepository.save(newUser);
    log.info("USER ADDED id: {}", newUser.getId());
  }

  public UserDTO serveFindUserById(@NonNull Long id) {
    Optional<User> user = userRepository.findById(id);
    if (!user.isPresent()) {
      throw new EntityNotFoundException("User not found");
    }

    return UserDTO.builder()
        .id(user.get().getId())
        .active(user.get().getActive())
        .email(user.get().getEmail())
        .createdAt(TimeFormater.formateTime(user.get()
            .getCreatedAt()))
        .build();
  }

  public void serveDeleteUserById(@NonNull Long id) {
    userRepository.deleteById(id);
  }

  public void serveDeleteUsersById(@NonNull List<Long> ids) {
    userRepository.deleteAllById(ids);
  }

  @SuppressWarnings("null")
  public Post serveAddPostForAuthenticatedUser(String text, MultipartFile[] files) throws Exception {

    String username = AuthService.getAuthenticatedUser().getUsername();
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));

    Post newPost = Post.builder()
        .user(user)
        .text(text)
        .author(username)
        .build();

    if (files != null) {
      String imagePath = null;
      for (MultipartFile imageFile : files) {
        imagePath = FileSaver.saveFileToPath(imageFile, uploadToPostImgs);
        PostImage postImage = new PostImage(imagePath, newPost);
        newPost.getImages().add(postImage);
      }
    }
    postRepository.save(newPost);
    return newPost;
  }

  public List<Post> getPostsForAuthenticatedUser() {

    String username = AuthService.getAuthenticatedUser().getUsername();
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));

    return user.getPosts();

  }
}
