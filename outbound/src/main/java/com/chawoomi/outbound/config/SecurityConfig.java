package com.chawoomi.outbound.config;

import com.chawoomi.core.exception.jwt.JwtAccessDeniedHandler;
import com.chawoomi.core.exception.jwt.JwtAuthenticationEntryPoint;
import com.chawoomi.core.redis.util.RedisUtil;
import com.chawoomi.core.util.HttpResponseUtil;
import com.chawoomi.outbound.adapter.KakaoLoginAdapter;
import com.chawoomi.outbound.adapter.authentication.CustomLogoutHandler;
import com.chawoomi.outbound.adapter.authentication.OAuthLoginFailureHandler;
import com.chawoomi.outbound.adapter.authentication.OAuthLoginSuccessHandler;
import com.chawoomi.outbound.util.JwtFilter;
import com.chawoomi.outbound.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final KakaoLoginAdapter kakaoLoginAdapter;
    private final OAuthLoginSuccessHandler oAuth2SuccessHandler;
    private final OAuthLoginFailureHandler oAuth2FailureHandler;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, RedisUtil redisUtil, JwtUtil jwtUtil) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))

                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(authz -> {
                    // 접근 허용
                    authz.requestMatchers(
                            "/swagger-ui/**",
                            "/swagger-resources/**",
                            "/v3/api-docs/**").permitAll();
                    // 그 외의 모든 요청은 인증 필요
                    authz.anyRequest().authenticated();
                })

                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(kakaoLoginAdapter))
                        .successHandler(oAuth2SuccessHandler)
                        .failureHandler(oAuth2FailureHandler))

                .addFilterAt(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler))

                .logout(logout -> logout
                        .logoutUrl("/api/v1/users/logout")
                        .addLogoutHandler(new CustomLogoutHandler(redisUtil, jwtUtil))
                        .logoutSuccessHandler((request, response, authentication)
                                -> HttpResponseUtil.setSuccessResponse(
                                response,
                                HttpStatus.OK,
                                "로그아웃 성공"
                        ))
                );

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/error", "/favicon.ico");
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "POST", "GET", "DELETE", "PUT"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
