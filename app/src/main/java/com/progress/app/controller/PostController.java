package com.progress.app.controller;

import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.progress.app.dto.PostDTO;
import com.progress.app.model.Post;
import com.progress.app.service.PostService;

import lombok.RequiredArgsConstructor;

import java.nio.file.Path;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

  @Value("${upload.imgs}")
  private String UPLOADS_DIR;

  private final PostService postService;

  @GetMapping("")
  public ResponseEntity<List<PostDTO>> getAllPosts() {
    return ResponseEntity.ok(postService.serveAllPost());
  }

  @PostMapping("/{post}")
  @ResponseStatus(code = HttpStatus.CREATED)
  public void addCommentToPost(@PathVariable Post post, @RequestBody String comment) {
    postService.serveAddCommentToPost(comment, post);
  }

  @SuppressWarnings("null")
  @GetMapping("/imgs/{name}")
  public ResponseEntity<Resource> serveImage(@PathVariable String name) {
    try {
      Path imagePath = Paths.get(UPLOADS_DIR).resolve(name);
      Resource resource = new UrlResource(imagePath.toUri());

      if (resource.exists() && resource.isReadable()) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // Adjust content type as needed
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } catch (MalformedURLException e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
