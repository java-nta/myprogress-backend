package com.progress.app.dto;


import com.progress.app.model.PostImage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {

  private Long id;
  private String author;
  private String text;
  private Long likeNbr;
  private String createdAt;
  private String updatedAt;
  private List<PostImage> images;

}
