package com.progress.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.progress.app.model.PostImage;

public interface PostImageRepository extends JpaRepository<PostImage, Long>{
  
}
