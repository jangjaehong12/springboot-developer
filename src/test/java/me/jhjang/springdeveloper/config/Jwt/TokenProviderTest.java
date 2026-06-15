package me.jhjang.springdeveloper.config.Jwt;

import io.jsonwebtoken.Jwts;
import me.jhjang.springdeveloper.config.jwt.JwtFactory;
import me.jhjang.springdeveloper.config.jwt.JwtProperties;
import me.jhjang.springdeveloper.config.jwt.TokenProvider;
import me.jhjang.springdeveloper.dao.User;
import me.jhjang.springdeveloper.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProperties jwtProperties;

    @DisplayName("generateToken() : 유저 정보와 만료 기간을 전달해 토큰을 생성")
    @Test
    void generateToken() {
        // given
        User testUser = userRepository
                .save(User.builder().email("wwg8947@g.yju.ac.kr")
                        .password("test").build());
        // When
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));

        // then
        Long userId = Jwts.parser().setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);
        assertThat(userId).isEqualTo(testUser.getId());
    }

    @DisplayName("validToken(): 만료된 토큰인 경우에 유효성 검증에 실패")
    @Test
    void validToken_invalidToken() {
        // given
        String token = JwtFactory.builder()
                .expiresAt(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .build().creatToken(jwtProperties);
        // when
        boolean result = tokenProvider.validateToken(token);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("validToken(): 유효한 토큰인 경우에 유효성 검증에 성공함")
    @Test
    void validToken_validToken() {
        //given
        String token = JwtFactory.withDefaultValues()
                .creatToken(jwtProperties);
        //when
        boolean result = tokenProvider.validateToken(token);

        //then
        assertThat(result).isTrue();
    }
}
