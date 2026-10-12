package com.example.blogging.blog.config;

import com.example.blogging.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {


    @Bean
    public UserDetailsService userDetailsService( UserRepository userRepository) {

        return email -> {
            com.example.blogging.blog.entity.User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with this email not found"));

            return User.builder()
                    .username(user.getUsername())
                    .password(passwordEncoder().encode(user.getPassword()))
                    .roles("IT").build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }
}
