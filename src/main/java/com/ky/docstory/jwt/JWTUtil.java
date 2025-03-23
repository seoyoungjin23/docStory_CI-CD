package com.ky.docstory.jwt;

import com.ky.docstory.common.code.DocStoryResponseCode;
import com.ky.docstory.common.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.Cookie;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {
    private static final int ACCESS_TOKEN_EXPIRATION_MS = 60 * 60 * 1000;
    private static final int COOKIE_EXPIRATION_SEC = 60 * 60;
    private final SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(Map<String, Object> claims) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION_MS);

        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(expiredDate)
                .signWith(secretKey)
                .compact();
    }

    public Cookie createJwtCookie(String token) {
        Cookie cookie = new Cookie("Authorization", token);
        cookie.setHttpOnly(true);
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(COOKIE_EXPIRATION_SEC);
        cookie.setAttribute("SameSite", "Lax");

        return cookie;
    }

    public boolean validateToken(String token) {
        try {
            getClaimsFromToken(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new BusinessException(DocStoryResponseCode.JWT_EXPIRED);
        } catch (MalformedJwtException e) {
            throw new BusinessException(DocStoryResponseCode.JWT_MALFORMED);
        } catch (SignatureException e) {
            throw new BusinessException(DocStoryResponseCode.JWT_BADSIGN);
        } catch (UnsupportedJwtException e) {
            throw new BusinessException(DocStoryResponseCode.JWT_UNSUPPORTED);
        } catch (DecodingException e) {
            throw new BusinessException(DocStoryResponseCode.JWT_DECODING);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(DocStoryResponseCode.JWT_ILLEGAL);
        } catch (Exception e) {
            throw new BusinessException(DocStoryResponseCode.JWT_UNAUTHORIZED);
        }
    }

    public String getProviderIdFromToken(String token) {
        return getClaimFromToken(token, "providerId");
    }

    private Claims getClaimsFromToken(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String getClaimFromToken(String token, String claimName) {
        return getClaimsFromToken(token).get(claimName, String.class);
    }
}
