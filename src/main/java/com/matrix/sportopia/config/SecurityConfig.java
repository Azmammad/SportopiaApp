package com.matrix.sportopia.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder passwordEncoder)
            throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf->csrf.disable())
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authorize-> authorize
                                .requestMatchers(permitAllUrls).permitAll()
                                .requestMatchers(adminUrls).hasAnyAuthority("ADMIN")
                                .requestMatchers(userUrls).hasAnyAuthority("USER")
                                .requestMatchers(userUrls).hasAnyAuthority("USER","ADMIN")
                                .requestMatchers(anyAuthUrls).authenticated()
                        //.anyRequest().authenticated()
                ).exceptionHandling(exceptionHandling-> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) ->
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED)
                        )
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                response.setStatus(HttpServletResponse.SC_FORBIDDEN)
                        )
                );
        return http.build();
    }

    static String[] permitAllUrls = {
            "/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/auth/**",
            "/api/sports/{id}",
            "/api/sports/sport-list",
            "/api/stadiums/{id}",
            "/api/stadiums/{sportId}",
            "/api/stadiums/list"
    };

    static String[] adminUrls = {
            "/controller/admin",
            "/api/stadiums/**",
            "/api/sports/**",
            "/api/reservations/**",
            "/api/users/noActive-list",
            "/api/users/list",
            "/api/users/change-status/{id}",
            "/api/users/",
    };

    static String[] userUrls = {
            "/api/users/password-update",
            "/api/users/update/{id}",
            "/api/reservations/**"
    };

    static String[] userAndAdminUrls = {
            "/api/users/{id}",
            "/api/users/photo",
            "/api/users/delete/{id}",
    };

    static String[] anyAuthUrls = {
            "/controller/any"
    };
}