package Cook.Cookify_SpringBoot.global;

import Cook.Cookify_SpringBoot.domain.member.security.SessionMember;
import Cook.Cookify_SpringBoot.domain.member.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .headers()
                .addHeaderWriter(new HeaderWriter() {
                    @Override
                    public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
                        response.setHeader("Cross-Origin-Opener-Policy", "same-origin; disallow-popups");
                    }
                })
                .and()
                // Your existing configurations
                .oauth2Login()
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService)
                .and()
                .successHandler((request, response, authentication) -> {
                    response.sendRedirect("http://localhost:3000"); // Redirect URL
                })
                .and()
                .authorizeRequests()
                .antMatchers("/oauth2/**", "/login/**", "/logout/**","/api/**", "/start/**", "/mypage/**", "/").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable() // Disable CSRF (only for testing environment)
                .cors(); // Enable CORS
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // 클라이언트의 출처를 명시적으로 추가
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
