package com.tom.gupy.Security.Config;

import com.tom.gupy.Security.TokenFilter.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.Security;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final String token = "KEkYXHde6iJ8fUXcoD283FAVYOOxGBLEooWYakpPmzcJMGlUyJDLWYhiZUOUUkj7JJBaL1G3BFXm9veoguRVozifZ4YeBOP4qVkWDsi9BP5xA97K8afTfzOmIotud5dsS9Kr0dMLCR5kiZszTCVjSKTomDvt3TEG9rRPcQKKGatCBH59srVo0UEz3abfe6s6dLI";
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests( request -> request.anyRequest().authenticated())
                .addFilterBefore(new TokenFilter(token), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManager.class);
    }
}
