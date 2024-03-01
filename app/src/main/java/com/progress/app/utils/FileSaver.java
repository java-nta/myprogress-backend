package com.progress.app.utils;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileSaver {

  private static final String SERVER_POST_IMGS_ENDPOINT = "http://localhost:8080/post/imgs/";

  public static String saveFileToPath(MultipartFile imageFile, String uploadDirectory) throws IOException {
    String uniqueFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

    Path rootLocation = Paths.get(uploadDirectory);
    Path destinationFile = rootLocation.resolve(uniqueFileName).normalize().toAbsolutePath();
    try (InputStream inputStream = imageFile.getInputStream()) {
      Files.copy(inputStream, destinationFile,
          StandardCopyOption.REPLACE_EXISTING);
    }
    return SERVER_POST_IMGS_ENDPOINT + uniqueFileName;

  }

  @SuppressWarnings("null")
  public static Resource loadFileAsResource(String fileName, String uploadDirectory) throws IOException {
    try {
      Path filePath = Paths.get(uploadDirectory).resolve(fileName).normalize();
      Resource resource = new UrlResource(filePath.toUri());
      if (resource.exists()) {
        return resource;
      } else {
        throw new IOException("File not found: " + fileName);
      }
    } catch (MalformedURLException ex) {
      throw new IOException("File not found: " + fileName, ex);
    }
  }
}
