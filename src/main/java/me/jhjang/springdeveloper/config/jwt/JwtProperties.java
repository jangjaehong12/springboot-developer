package me.jhjang.springdeveloper.config.jwt;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt") //application.yml 의 jwt 프로퍼티 값을 가져와서 사용하기 위한 애너테이션
public class JwtProperties {
    private String issuer;
    private String secretKey;

}
