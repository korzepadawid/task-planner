package io.github.korzepadawid.springtaskplanning.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import io.github.korzepadawid.springtaskplanning.config.AWSS3Config;
import io.github.korzepadawid.springtaskplanning.exception.BusinessLogicException;
import io.github.korzepadawid.springtaskplanning.service.StorageService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AWSS3StorageService implements StorageService {

  Logger log = LoggerFactory.getLogger(AWSS3StorageService.class);

  private final AWSS3Config awss3Config;
  private final AmazonS3 amazonS3;

  public static final long BYTES_LIMIT = 1000000;

  public AWSS3StorageService(AWSS3Config awss3Config, AmazonS3 amazonS3) {
    this.awss3Config = awss3Config;
    this.amazonS3 = amazonS3;
  }

  @Override
  public String uploadPhoto(MultipartFile multipartFile) {
    String storageKey = UUID.randomUUID().toString();
    putPhoto(storageKey, multipartFile);
    return storageKey;
  }

  @Async
  @Override
  public void replacePhoto(String storageKey, MultipartFile file) {
    putPhoto(storageKey, file);
  }

  private void putPhoto(String storageKey, MultipartFile multipartFile) {
    final List<String> possibleExtensions = Arrays.asList(".jpeg", ".png", ".jpg");

    if (multipartFile == null) {
      throw new BusinessLogicException("File doesn't supported");
    }

    possibleExtensions.stream()
        .filter(
            extension -> {
              String filename = multipartFile.getOriginalFilename();
              return filename != null && filename.endsWith(extension);
            })
        .findAny()
        .orElseThrow(() -> new BusinessLogicException("File doesn't supported"));

    File file = convertMultiPartFileToFile(multipartFile);

    if (file.length() > BYTES_LIMIT) {
      throw new BusinessLogicException("File size too big.");
    }

    try {
      amazonS3.putObject(awss3Config.getBucketName(), storageKey, file);
      file.delete();
    } catch (AmazonServiceException e) {
      log.error("Can't put object to s3 " + e.getErrorMessage());
    }
  }

  private File convertMultiPartFileToFile(MultipartFile file) {
    File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
    try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
      fos.write(file.getBytes());
    } catch (IOException e) {
      log.error("Error converting multipartFile to file", e);
    }
    return convertedFile;
  }
}
