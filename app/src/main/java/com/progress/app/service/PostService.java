package com.progress.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progress.app.model.Post;
import com.progress.app.repository.PostRepository;

@Service
public class PostService {
  @Autowired
  private PostRepository postRepository;

  public List<Post> serveAllPost(){
    return postRepository.findAll();
  }
}
