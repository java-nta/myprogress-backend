package com.progress.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progress.app.dto.UserDto;
import com.progress.app.model.Post;
import com.progress.app.model.User;
import com.progress.app.request.AddUserRequest;
import com.progress.app.request.PostRequest;
import com.progress.app.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserService userService;

  @GetMapping("")
  public ResponseEntity<List<User>> getAllUsers() {
    return ResponseEntity.ok(userService.serveAllUsers());
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getUserById(@PathVariable @NonNull Long id) {

    return ResponseEntity.ok(new UserDto(userService.serveFindUserById(id)));
  }

  @PostMapping("")
  public ResponseEntity<User> addUser(@RequestBody @Valid AddUserRequest addUserRequest) throws Exception {
    return new ResponseEntity<>(
        userService.serveAdduser(
            addUserRequest.getUsername(),
            addUserRequest.getEmail(),
            addUserRequest
                .getPassword()),
        HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable Long id) {
    if (id != null) {
      userService.serveDeleteUserById(id);
      return ResponseEntity.ok("User deleted");
    } else {
      return ResponseEntity.badRequest().body("Invalid user ID");
    }
  }

  @DeleteMapping("")
  public ResponseEntity<String> deleteUser(@RequestBody List<Long> ids) throws Exception {
    if (ids == null) {
      throw new Exception("Invalid ids");
    }
    userService.serveDeleteUsersById(ids);
    return ResponseEntity.ok("Users Deleted.");
  }

  @PostMapping("/post")
  public ResponseEntity<String> addPostForAuthenticatedUser(@RequestBody @Valid PostRequest post) {
    userService.serveAddPostForAuthenticatedUser(post.getText());
    return ResponseEntity.ok("Post added");
  }

  @GetMapping("/post/me")
  public ResponseEntity<List<Post>> getPost() {

    return ResponseEntity.ok(userService.getPostsForAuthenticatedUser());
  }

}
