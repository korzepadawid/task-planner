package io.github.korzepadawid.springtaskplanning.security;

public interface JwtProvider {

  String generateToken(UserPrincipal userPrincipal);
}
