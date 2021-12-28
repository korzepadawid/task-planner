package io.github.korzepadawid.springtaskplanning.service;

import java.io.IOException;
import java.util.Collection;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

  void putFile(
      MultipartFile multipartFile,
      Collection<String> extensions,
      int maxLimitInBytes,
      String storageKey)
      throws IOException;

  byte[] downloadFile(String storageKey) throws IOException;
}
