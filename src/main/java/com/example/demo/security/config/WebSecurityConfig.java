package com.example.demo.security.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration

@AllArgsConstructor

@EnableWebSecurity

public class WebSecurityConfig {

    @Bean

    public BCryptPasswordEncoder BCryptPasswordEncoder(){

        return new BCryptPasswordEncoder();

    }

    @Bean

    public AuthenticationManager authManager(UserDetailsService detailsService){

        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();

        daoProvider.setUserDetailsService(detailsService);

        return new ProviderManager(daoProvider);

    }

    @Bean

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http

                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> {

                    auth.requestMatchers("/api/v**/registration/**", "/error").permitAll();

                    try {
                        auth.anyRequest().authenticated().and().formLogin();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                })

                .httpBasic(Customizer.withDefaults())

                .build();

    }

}