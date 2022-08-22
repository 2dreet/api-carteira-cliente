package br.com.carteira.cliente.security;

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
		http.csrf().disable();
		http.cors().and().authorizeRequests().antMatchers(HttpMethod.POST, "/user/auth").permitAll()
				.antMatchers(HttpMethod.POST, "/user").permitAll()
				.antMatchers(HttpMethod.PUT, "/user/forgot-password").permitAll()
				.anyRequest().authenticated().and()
				.addFilter(new JwtAuthenticationFilter(authenticationManager))
				.addFilter(new JwtAuthorizationFilter(authenticationManager)).sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.httpBasic().and().exceptionHandling()
				.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
		;
		
		return http.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
		source.registerCorsConfiguration("/**", corsConfiguration);

		return source;
	}
}
