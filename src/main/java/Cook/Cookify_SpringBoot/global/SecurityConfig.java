package Cook.Cookify_SpringBoot.global;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                // 다른 설정들 추가
                .oauth2Login()
                .successHandler((request, response, authentication) -> {
                    // 로그인 성공 후의 동작 정의
                    response.sendRedirect("http://localhost:3000"); // 리다이렉트할 URL
                })
                .and()
                .authorizeRequests()
                .antMatchers("/oauth2/**", "/login/**", "/logout/**").permitAll() // 허용할 URL 패턴 추가
                .anyRequest().authenticated()
                .and()
                .csrf().disable(); // CSRF 비활성화 (테스트 환경에서만)
    }
}

