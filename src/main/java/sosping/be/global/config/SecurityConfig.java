package sosping.be.global.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import sosping.be.global.filter.AuthenticationTokenFilter;
import sosping.be.global.filter.BusinessExceptionHandlerFilter;
import sosping.be.global.filter.CustomCorsFilter;
import sosping.be.global.filter.ServletFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomCorsFilter customCorsFilter;
    private final AuthenticationTokenFilter authenticationTokenFilter;
    private final BusinessExceptionHandlerFilter businessExceptionHandlerFilter;
    private final ServletFilter servletFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CORS 설정
                .addFilterBefore(customCorsFilter, CorsFilter.class)
                // 로그필터
                .addFilterBefore(servletFilter, CustomCorsFilter.class)
                // CSRF 비활성화
                .csrf(csrf -> csrf.disable())
                // Session 비활성화
                .sessionManagement(session -> session.disable())
                // 폼 로그인 비활성화
                .formLogin(form -> form.disable())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/home/**", "/index/**", "/index.js", "/favicon.ico", "/swagger-ui/**", "/v3/**", "/api/todo-list/**", "/ws/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated())

                // jwt 인증 토큰 설정
                .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(businessExceptionHandlerFilter, AuthenticationTokenFilter.class);


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


