package com.ruty.rutyserver.security;

import com.ruty.rutyserver.repository.MemberRepository;
import com.ruty.rutyserver.security.jwt.JwtFilter;
import com.ruty.rutyserver.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    private static final String[] PERMIT_URL_ARRAY = {
            // permit uri
            "/login/**",

            /* swagger v2 */
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            /* swagger v3 */
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui/swagger-ui-standalone-preset.js",
            "/swagger-ui/swagger-initializer.js",
            "/swagger-ui/swagger-ui-bundle.js",
            "/swagger-ui/swagger-ui.css",
            "/swagger-ui/index.css",
            "/swagger-ui/favicon-32x32.png",
            "/swagger-ui/favicon-16x16.png",
            "/api-docs/json/swagger-config",
            "/api-docs/json"
    };
    @Bean
    public JwtFilter jwtFilter() {
        JwtFilter jwtFilter = new JwtFilter(jwtService, memberRepository);
        return jwtFilter;
    }

    @Bean
    public WebSecurityCustomizer configure() throws Exception {
        return web -> {
            web.ignoring()
                    .requestMatchers(PathRequest
                            .toStaticResources().atCommonLocations());
            web.ignoring().requestMatchers("/swagger-ui/*", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/v3/api-docs");
        };
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // 토큰 사용하기에 csrf 불가능
                .cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
                    // cors configuration 처리를 위한 spring security 설정
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration configuration = new CorsConfiguration();
                        configuration.setAllowedOrigins(Arrays.asList(
                                "http://localhost:3000",
                                "https://e18f-121-166-16-179.ngrok-free.app"
                        ));
                        configuration.setAllowedMethods(Collections.singletonList(">"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);

                        configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));
                        configuration.setExposedHeaders(Collections.singletonList("Authorization-refresh"));
                        return configuration;
                    }
                }))
                .formLogin(login -> login.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .headers(header -> header.frameOptions(frameOption -> frameOption.disable()))
                .authorizeHttpRequests(request -> request
                                .requestMatchers(PERMIT_URL_ARRAY).permitAll()
                                .anyRequest().authenticated()
//                        .requestMatchers("/**").permitAll()
                )
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }


}
