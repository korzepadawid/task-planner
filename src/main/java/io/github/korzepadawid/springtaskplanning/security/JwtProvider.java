package io.github.korzepadawid.springtaskplanning.security;

public interface JwtProvider {

  String generateToken(UserPrincipal userPrincipal);

  Boolean verify(String jwt);

  String extractEmailFromJwt(String jwt);
}
