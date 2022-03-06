package io.github.korzepadawid.springtaskplanning.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.korzepadawid.springtaskplanning.config.JwtConfig;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JwtProviderImpl implements JwtProvider {

  private final JwtConfig jwtConfig;

  private static final int SECOND_IN_MILLIS = 1000;

  private static final Logger log = LoggerFactory.getLogger(JwtProviderImpl.class);

  public JwtProviderImpl(JwtConfig jwtConfig) {
    this.jwtConfig = jwtConfig;
  }

  @Override
  public String generateToken(UserPrincipal userPrincipal) {
    long expiringAt =
        System.currentTimeMillis()
            + jwtConfig.getAccessTokenExpirationInSeconds() * SECOND_IN_MILLIS;
    Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecret());

    return JWT.create()
        .withIssuer(userPrincipal.getEmail())
        .withExpiresAt(new Date(expiringAt))
        .sign(algorithm);
  }

  @Override
  public Boolean verify(String jwt) {
    return decodeJwt(jwt) != null;
  }

  @Override
  public String extractEmailFromJwt(String jwt) {
    DecodedJWT decodedJWT = decodeJwt(jwt);
    assert decodedJWT != null;
    return decodedJWT.getIssuer();
  }

  private DecodedJWT decodeJwt(String jwt) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecret());
      JWTVerifier verifier = JWT.require(algorithm).build();
      return verifier.verify(jwt);
    } catch (JWTVerificationException exception) {
      log.error("Invalid signature for " + jwt);
    }
    return null;
  }
}
