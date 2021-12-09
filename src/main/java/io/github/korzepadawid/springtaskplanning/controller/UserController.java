package io.github.korzepadawid.springtaskplanning.controller;

import com.amazonaws.util.IOUtils;
import io.github.korzepadawid.springtaskplanning.dto.UserResponse;
import io.github.korzepadawid.springtaskplanning.security.CurrentlyAuthenticatedUser;
import io.github.korzepadawid.springtaskplanning.security.UserPrincipal;
import io.github.korzepadawid.springtaskplanning.service.UserService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/me")
  public ResponseEntity<UserResponse> findCurrentUser(
      @CurrentlyAuthenticatedUser UserPrincipal userPrincipal) {
    UserResponse userResponse = userService.findUserByEmail(userPrincipal.getEmail());
    return new ResponseEntity<>(userResponse, HttpStatus.OK);
  }

  @PutMapping("/me/avatar")
  public ResponseEntity<Void> setAvatar(
      @CurrentlyAuthenticatedUser UserPrincipal userPrincipal,
      @RequestParam("file") MultipartFile multipartFile)
      throws IOException {
    userService.setAvatar(userPrincipal.getEmail(), multipartFile);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/{id}/avatar")
  public void getAvatar(@PathVariable Long id, HttpServletResponse httpServletResponse)
      throws IOException {
    byte[] avatarByUserId = userService.findAvatarByUserId(id);
    httpServletResponse.setContentType("image/jpeg");
    httpServletResponse.getOutputStream();
    InputStream is = new ByteArrayInputStream(avatarByUserId);
    IOUtils.copy(is, httpServletResponse.getOutputStream());
  }
}
