package com.algrince.finaltask.config;

import com.algrince.finaltask.services.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.Provider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTFilter jwtFilter;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/home").permitAll()
                    .requestMatchers("/registration").permitAll()
                    .anyRequest().permitAll()
            )
            .formLogin(form -> form
                    .loginPage("/login")
                    .loginProcessingUrl("/process_login")
                    .defaultSuccessUrl("/home", true)
                    .failureUrl("/login?error")
                    .permitAll()
            )
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
            )
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors();
        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
                                                       UserDetailsService userDetailsService,
                                                       BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }
}
