package io.github.korzepadawid.springtaskplanning.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import io.github.korzepadawid.springtaskplanning.config.AWSS3Config;
import io.github.korzepadawid.springtaskplanning.exception.StorageException;
import io.github.korzepadawid.springtaskplanning.service.StorageService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AWSS3StorageService implements StorageService {

  Logger log = LoggerFactory.getLogger(AWSS3StorageService.class);

  private final AWSS3Config s3Config;
  private final AmazonS3 s3;

  public AWSS3StorageService(AWSS3Config s3Config, AmazonS3 s3) {
    this.s3Config = s3Config;
    this.s3 = s3;
  }

  @Override
  public void putFile(
      MultipartFile multipartFile,
      Collection<String> extensions,
      int maxLimitInBytes,
      String storageKey) {

    if (!isValidExtension(multipartFile, extensions)) {
      throw new StorageException("File extension doesn't supported.");
    }

    File file = convertMultiPartFileToFile(multipartFile);

    if (!isValidSize(file, maxLimitInBytes)) {
      throw new StorageException("Maximum file size is " + maxLimitInBytes + " bytes.");
    }

    try {
      s3.putObject(s3Config.getBucketName(), storageKey, file);
      file.delete();
    } catch (AmazonServiceException e) {
      log.error("Can't put {}", storageKey);
    }
  }

  @Override
  public byte[] downloadFile(String storageKey) {
    try {
      S3Object s3Object = s3.getObject(s3Config.getBucketName(), storageKey);
      S3ObjectInputStream inputStream = s3Object.getObjectContent();
      return IOUtils.toByteArray(inputStream);
    } catch (AmazonServiceException e) {
      log.error("Can't download {}", storageKey);
    } catch (IOException e) {
      log.error("I/O error for {}", storageKey);
    }
    return null;
  }

  private Boolean isValidSize(File file, int maxLimitInBytes) {
    return file.length() <= maxLimitInBytes;
  }

  private Boolean isValidExtension(MultipartFile multipartFile, Collection<String> extensions) {
    return extensions.stream()
        .anyMatch(
            extension -> {
              if (!multipartFile.isEmpty()) {
                return Objects.requireNonNull(multipartFile.getOriginalFilename())
                    .toLowerCase(Locale.ROOT)
                    .endsWith(extension.toLowerCase(Locale.ROOT));
              }
              return false;
            });
  }

  private File convertMultiPartFileToFile(MultipartFile file) {
    File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
    try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
      fos.write(file.getBytes());
    } catch (IOException e) {
      log.error("Error converting multipartFile to file");
    }
    return convertedFile;
  }
}
