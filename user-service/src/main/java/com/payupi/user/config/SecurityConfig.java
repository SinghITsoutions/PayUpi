package com.payupi.user.config;
//
//
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//   @Bean
//public SecurityFilterChain securityFilterChain(HttpSecurity http)
//        throws Exception {
//
//    http
//            .csrf(csrf -> csrf.disable())
//            .sessionManagement(session ->
//                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//            .authorizeHttpRequests(auth -> auth
//
//                    .requestMatchers(
//                            "/auth/register",
//                            "/auth/login",
//                            "/auth/verify-email")
//                    .permitAll()
//
//                    .requestMatchers("/users/**")
//                    .hasAnyRole("USER", "ADMIN")
//
//                    .anyRequest()
//                    .authenticated()
//            );
//
//    return http.build();
//}
//}



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




//
//                        .requestMatchers("/admin/**")
//                        .hasRole("ADMIN")
//
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(Customizer.withDefaults());
//
//        return http.build();

                http
                        .csrf(csrf -> csrf.disable())
                        .cors(Customizer.withDefaults())
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/auth/**").permitAll()
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