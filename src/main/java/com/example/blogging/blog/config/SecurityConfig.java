package com.example.blogging.blog.config;

import com.example.blogging.blog.security.JwtAuthenticationEntryPoint;
import com.example.blogging.blog.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.cors(AbstractHttpConfigurer::disable)
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/login")
                        .permitAll()
                                .requestMatchers( "/login",
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html")
                                .permitAll()
                        .requestMatchers(HttpMethod.GET)
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .exceptionHandling(exception-> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(https -> {
                });

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
