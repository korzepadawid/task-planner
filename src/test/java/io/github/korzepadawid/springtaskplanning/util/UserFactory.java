package io.github.korzepadawid.springtaskplanning.util;

import io.github.korzepadawid.springtaskplanning.dto.AuthRegisterRequest;
import io.github.korzepadawid.springtaskplanning.model.AuthProvider;
import io.github.korzepadawid.springtaskplanning.model.User;

public abstract class UserFactory {

  public static User getUser(AuthProvider authProvider) {
    User user = new User();
    user.setName("billmurray");
    user.setEmail("billmurray@gmail.com");
    user.setAuthProvider(authProvider);

    if (authProvider.equals(AuthProvider.LOCAL)) {
      user.setPassword("p@s5w0rD!@3");
    }

    user.setId(100L);

    return user;
  }

  public static AuthRegisterRequest getUserRegisterRequest() {
    User user = getUser(AuthProvider.LOCAL);
    AuthRegisterRequest authRegisterRequest = new AuthRegisterRequest();
    authRegisterRequest.setName(user.getName());
    authRegisterRequest.setEmail(user.getEmail());
    authRegisterRequest.setPassword(user.getPassword());

    return authRegisterRequest;
  }
}
