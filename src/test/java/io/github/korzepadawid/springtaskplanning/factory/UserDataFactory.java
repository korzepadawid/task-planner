package io.github.korzepadawid.springtaskplanning.factory;

import io.github.korzepadawid.springtaskplanning.model.AuthProvider;
import io.github.korzepadawid.springtaskplanning.model.User;

public class UserDataFactory {

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
}
