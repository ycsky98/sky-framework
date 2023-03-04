package org.sky.framework.config.jwt;

import io.jsonwebtoken.*;
import org.sky.framework.config.jwt.properties.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @author yangcong
 *
 * jwt校验认证配置
 */
@Configuration
public class JwtConfig {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 构建token
     *
     * @param subject 信息主体(一般用户名)
     * @return
     */
    public String createToken(String subject){
        Date now = new Date();
        Date expireTime = new Date(now.getTime() + this.jwtProperties.getExpire() * 1000);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expireTime)
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret())
                .compact();
    }

    /**
     * 获取token的注册信息(token创建时纳入的主体)
     * @param token
     * @return
     */
    public Claims getTokenClaim(String token){
        try {
            return Jwts.parser().setSigningKey(this.jwtProperties.getSecret()).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |
                SignatureException | IllegalArgumentException exception){
            throw exception;
        }
    }

    /**
     * 清除登陆状态
     *
     * @param token
     */
    public void clearToken(String token){
        this.getTokenClaim(token).clear();
    }
}
