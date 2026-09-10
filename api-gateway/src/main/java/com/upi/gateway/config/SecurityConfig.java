//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Autowired
//    private GatewayJwtFilter jwtFilter;
//
//    public SecurityConfig(GatewayJwtFilter jwtFilter) {
//        this.jwtFilter = jwtFilter;
//    }
//
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http.csrf().disable()
//                .authorizeHttpRequests()
//                .requestMatchers("/auth/**").permitAll() // allow login
//                .anyRequest().authenticated()
//                .and()
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//}