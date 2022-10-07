package br.com.carteira.cliente.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.carteira.cliente.security.jwt.JwtAuthenticationFilter;
import br.com.carteira.cliente.security.jwt.JwtAuthorizationFilter;

@Configuration
public class ServerSecurity {

	@Autowired
	AuthenticationManager authenticationManager;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().
			 csrf().disable().
			 authorizeRequests().antMatchers(HttpMethod.POST, "/user/auth").permitAll()
				.antMatchers(HttpMethod.POST, "/user").permitAll()
				.antMatchers(HttpMethod.PUT, "/user/forgot-password").permitAll()
				.anyRequest().authenticated().and()
				.addFilter(new JwtAuthenticationFilter(authenticationManager))
				.addFilter(new JwtAuthorizationFilter(authenticationManager)).sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		return http.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
		
		List<String> val = new ArrayList<>();
		val.add("*");
		
		corsConfiguration.setAllowedOrigins(val);
		corsConfiguration.setAllowedMethods(val);
		corsConfiguration.setAllowedHeaders(val);
		
		source.registerCorsConfiguration("/**", corsConfiguration);

		return source;
	}
}
