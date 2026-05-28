package me.jhjang.springdeveloper.config;

import lombok.RequiredArgsConstructor;
import me.jhjang.springdeveloper.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailService userDetailServiceService;

    @Bean
    public WebSecurityCustomizer configure() {
        // toH2Console과 /static 폴더 아래의 자바스크립트 요청은 인증하지 않도록 설정
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/static/**"));
    }




//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 1. 로그인 페이지, 회원가입 페이지 요청 등은 인증 필터를 거치지 않고 바로 컨트롤러로 전달 되도록 설정
        // 2. 그 외에 모든 요청은 인증을 거치도록 설정
        // 3. 로그인 폼 페이지 URL 설정
        // 4. 로그인 성공 시 어느 페이지로 갈지 URL 설정 (목록보기 페이지 URL)
        // 5. 로그아웃이 성공했을 때 어느 페이지로 갈지 URL 설정 (로그인 폼 페이지)
        // 6. 로그아웃 했을 때 세션 정보를 무효화 할지 여부를 설정 (true)

