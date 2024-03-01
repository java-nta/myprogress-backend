package com.progress.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.progress.app.dto.UserDTO;
import com.progress.app.model.Post;
import com.progress.app.request.AddUserRequest;
import com.progress.app.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("")

  public ResponseEntity<List<UserDTO>> getAllUsers() {
    return ResponseEntity.ok(userService.serveAllUsers());
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> getUserById(@PathVariable @NonNull Long id) {
    return ResponseEntity.ok(userService.serveFindUserById(id));
  }

  @PostMapping("")
  @ResponseStatus(code = HttpStatus.CREATED)
  public void addUser(@RequestBody @Valid AddUserRequest addUserRequest) throws Exception {
    userService.serveAdduser(
        addUserRequest.getUsername(),
        addUserRequest.getEmail(),
        addUserRequest.getPassword());
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
  public ResponseEntity<Object> addPostForAuthenticatedUser(
      @RequestParam(name = "text", required = false) String text,
      @RequestParam(name = "images", required = false) MultipartFile[] files) throws Exception {
    if (text == null && files == null) {
      return ResponseEntity.badRequest().body("Invalid request");
    }
    return new ResponseEntity<>(userService.serveAddPostForAuthenticatedUser(text, files), HttpStatus.CREATED);
  }

  @GetMapping("/post/me")
  public ResponseEntity<List<Post>> getPost() {

    return ResponseEntity.ok(userService.getPostsForAuthenticatedUser());
  }

}
