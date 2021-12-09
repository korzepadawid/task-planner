package io.github.korzepadawid.springtaskplanning.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

  String uploadPhoto(MultipartFile file) throws IOException;

  void replacePhoto(String storageKey, MultipartFile file);

  byte[] downloadPhoto(String storageKey);
}
