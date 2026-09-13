package com.payupi.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(
		exclude = UserDetailsServiceAutoConfiguration.class
)

@Slf4j
public class GatewayServiceApplication {

	public static void main(String[] args) {
       log.info("hello vishvjeet");
		SpringApplication.run(GatewayServiceApplication.class, args);

	}
}
