package me.jhjang.springdeveloper.config;

import lombok.RequiredArgsConstructor;
import me.jhjang.springdeveloper.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsService userService;

    @Bean
    public WebSecurityCustomizer configure() {
        // toH2Console과 /static 폴더 아래의 자바스크립트 요청은 인증하지 않도록 설정
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/static/**"));
    }





    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 1. 로그인 페이지, 회원가입 페이지 요청 등은 인증 필터를 거치지 않고 바로 컨트롤러로 전달 되도록 설정
        // 2. 그 외에 모든 요청은 인증을 거치도록 설정
        // 3. 로그인 폼 페이지 URL 설정
        // 4. 로그인 성공 시 어느 페이지로 갈지 URL 설정 (목록보기 페이지 URL)
        // 5. 로그아웃이 성공했을 때 어느 페이지로 갈지 URL 설정 (로그인 폼 페이지)
        // 6. 로그아웃 했을 때 세션 정보를 무효화 할지 여부를 설정 (true)
    return  http.authorizeHttpRequests(auth->auth
            .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/login"),
                    PathPatternRequestMatcher.withDefaults().matcher("/signup"),
                    PathPatternRequestMatcher.withDefaults().matcher("/user")).permitAll()
            .anyRequest().authenticated())
            .formLogin(formLogin->formLogin.loginPage("/login")
                    .defaultSuccessUrl("/articles")
            )
            .logout(logout->logout.logoutSuccessUrl("/login")
                    .invalidateHttpSession(true))
            .csrf(AbstractHttpConfigurer::disable)
            .build();
}

@Bean
public AuthenticationManager authenticationManager(HttpSecurity http,
                                                   BCryptPasswordEncoder bCryptPasswordEncoder,
                                                   UserDetailService userDetailService) throws Exception {
    /*
        1. Spring Security가 사용자 인증을 위해서 사용할 AuthenticationProvider 생성
        2. AuthenticationProvider가 DB에서 사용자 정보를 읽어오기 위해 사용할 서비스 설정
        3. AuthenticationProvider가 사용자 암호를 암호화하기 위해 사용할 encoder 설정
        4. AuthenticationManager에게 위에서 생성 및 설ㅈ어한 AuthenticationProvider 전달
     */
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailService);
    authProvider.setPasswordEncoder(bCryptPasswordEncoder);
    return new ProviderManager(authProvider);
}

@Bean
public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
}
}