package com.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig{

	@Bean
	SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
		return http
						.authorizeExchange()
						.anyExchange().authenticated()
						.and().authenticationManager(reactiveAuthenticationManager())
						.httpBasic().and()
						.csrf().disable()
						.build();
	}

	@Bean
	ReactiveAuthenticationManager reactiveAuthenticationManager(){
		return new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsRepository());
	}

	@Bean
	public MapReactiveUserDetailsService userDetailsRepository() {
		User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
		UserDetails hubert = userBuilder.username("user").password("secret").roles("USER").build();
		UserDetails admin = userBuilder.username("admin").password("secret").roles("USER", "ADMIN").build();
		return new MapReactiveUserDetailsService(hubert, admin);
	}
}
