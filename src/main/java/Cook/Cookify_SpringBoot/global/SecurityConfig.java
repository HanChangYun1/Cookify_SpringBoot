package Cook.Cookify_SpringBoot.global;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.HeaderWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .headers()
                .addHeaderWriter(new HeaderWriter() {
                    @Override
                    public void writeHeaders(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) {
                        response.setHeader("Cross-Origin-Opener-Policy", "same-origin; disallow-popups");
                    }
                })
                .and()
                // Your existing configurations
                .oauth2Login()
                .successHandler((request, response, authentication) -> {
                    // Define actions after successful login
                    response.sendRedirect("http://localhost:3000"); // Redirect URL
                })
                .and()
                .authorizeRequests()
                .antMatchers("/oauth2/**", "/login/**", "/logout/**","/start/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable(); // Disable CSRF (only for testing environment)
    }
}
