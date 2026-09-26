package com.payupi.user.config;

import com.payupi.user.filter.JwtAuthenticationFilter;
import com.payupi.user.security.OAuth2SuccessHandler;
import com.payupi.user.service.impl.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) // in this bean method name could be anything not securityFilterChain is requird
            throws Exception {





                http
                        .csrf(csrf -> csrf.disable())
                        .cors(Customizer.withDefaults())
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/auth/**",
                                                 "/oauth2/**",
                                         "/login/**").permitAll()
                                .anyRequest().authenticated()



                        )
                      .oauth2Login(oauth -> oauth
                                 .userInfoEndpoint(userInfo ->
                                  userInfo.userService(customOAuth2UserService)
                      )
                        .successHandler(oAuth2SuccessHandler)
                      )

                        .addFilterBefore(
                                jwtAuthenticationFilter,
                                UsernamePasswordAuthenticationFilter.class

                        );




                return http.build();




    }
}