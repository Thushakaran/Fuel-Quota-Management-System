package com.se.Fuel_Quota_Management_System.security;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(@NotNull HttpSecurity http)
            throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(
                                        "/api/auth/**",
                                        "/api/fuel-station/**",
                                        "/api/owner/**",
                                        "/api/v1/**",
                                        "/api/admin/**",
                                        "/api/transactions/**",
                                        "/api/vehicles/**",
                                        "/api/transactions/updateFuelQuota")
                                .permitAll()
//                                .requestMatchers("api/fuel-station/**").hasAuthority("station")
//                                .requestMatchers("api/owner/**").hasAuthority("stationowner")
//                                .requestMatchers("/api/admin/**").hasRole("admin")
                                .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy
                                (SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager
            (@NotNull AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
