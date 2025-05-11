package com.ecommerce.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.ecommerce.api.config.filter.JwtTokenValidator;
import com.ecommerce.api.services.UserDetailServiceImpl;
import com.ecommerce.api.utils.JWTutils;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    private JWTutils jwtUtils;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationProvider authenticationProvider)
            throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    http.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/uploads/**").permitAll();
                    http.requestMatchers(HttpMethod.GET, "api/category/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, "api/category/**").permitAll();
                    http.requestMatchers(HttpMethod.PUT, "api/category/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "api/category/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.GET, "api/product/**").permitAll();
                    http.requestMatchers(HttpMethod.GET, "api/roles/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, "api/product/**").permitAll();
                    http.requestMatchers(HttpMethod.PUT, "api/product/**").permitAll();
                    http.requestMatchers(HttpMethod.DELETE, "api/product/**").permitAll();
                    http.requestMatchers(HttpMethod.GET, "api/user/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, "api/user/**").permitAll();
                    http.requestMatchers(HttpMethod.PUT, "api/user/**").permitAll();
                    http.requestMatchers(HttpMethod.DELETE, "api/user/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.GET, "api/order/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, "api/order/**").permitAll();
                    http.requestMatchers(HttpMethod.PUT, "api/order/**").permitAll();
                    http.requestMatchers(HttpMethod.DELETE, "api/order/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.GET, "api/cart/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, "api/cart/**").permitAll();
                    http.requestMatchers(HttpMethod.PUT, "api/cart/**").hasAnyRole("ADMIN", "CLIENT");
                    http.requestMatchers(HttpMethod.DELETE, "api/cart/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.GET, "api/marker/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, "api/marker/**").permitAll();
                    http.requestMatchers(HttpMethod.PUT, "api/marker/**").hasAnyRole("ADMIN", "SELLER");
                    http.requestMatchers(HttpMethod.DELETE, "api/marker/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.GET, "api/payment/**").hasAnyRole("ADMIN", "SELLER", "CLIENT");
                    http.requestMatchers(HttpMethod.POST, "api/payment/**").permitAll();
                    http.requestMatchers(HttpMethod.PUT, "api/payment/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "api/payment/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.GET, "api/permission/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, "api/permission/**").permitAll();
                    http.requestMatchers(HttpMethod.PUT, "api/permission/**").hasAnyRole("ADMIN", "SELLER");
                    http.requestMatchers(HttpMethod.DELETE, "api/permission/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.GET, "api/roles/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, "api/roles/**").permitAll();
                    http.requestMatchers(HttpMethod.PUT, "api/roles/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "api/roles/**").hasAnyRole("ADMIN");
                    http.anyRequest().authenticated();
                })
                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
