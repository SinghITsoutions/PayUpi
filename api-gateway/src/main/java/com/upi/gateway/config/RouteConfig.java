//package com.upi.gateway.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RouteConfig {
//
//    @Bean
//    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
//
//                // User Service Routes
//                .route("user-service", r -> r.path("/users/**")
//                        .uri("http://localhost:8081"))
//
//                // Bank Service Routes
//                .route("bank-service", r -> r.path("/banks/**")
//                        .uri("http://localhost:8082"))
//
//                // Transaction Service Routes
//                .route("transaction-service", r -> r.path("/transactions/**")
//                        .uri("http://localhost:8083"))
//
//                .build();
//    }
//}
