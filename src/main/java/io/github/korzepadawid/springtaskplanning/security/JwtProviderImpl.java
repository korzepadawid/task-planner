package io.github.korzepadawid.springtaskplanning.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.github.korzepadawid.springtaskplanning.config.JwtConfig;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtProviderImpl implements JwtProvider {

  private final JwtConfig jwtConfig;

  public JwtProviderImpl(JwtConfig jwtConfig) {
    this.jwtConfig = jwtConfig;
  }

  @Override
  public String generateToken(UserPrincipal userPrincipal) {
    long expiringAt =
        System.currentTimeMillis() + jwtConfig.getAccessTokenExpirationInSeconds() * 1000;
    Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecret());
    return JWT.create()
        .withIssuer(userPrincipal.getEmail())
        .withExpiresAt(new Date(expiringAt))
        .sign(algorithm);
  }
}
