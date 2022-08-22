package br.com.carteira.cliente.security.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.carteira.cliente.constants.SecurityConstants;
import br.com.carteira.cliente.request.UserAuthRequest;
import br.com.carteira.cliente.security.UserAuth;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	AuthenticationManager authenticationManager;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		setFilterProcessesUrl("/user/auth");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException, BadCredentialsException {
		try {
			UserAuthRequest creds = new ObjectMapper().readValue(req.getInputStream(), UserAuthRequest.class);

			res.setContentType("application/json");
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(creds.getLogin(), creds.getPassword(), new ArrayList<>()));
		} catch (Exception e) {
			SecurityContextHolder.clearContext();
			this.logger.error(e.getMessage());
			throw new BadCredentialsException(e.getMessage());
		}
	}
	
	@Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {
		
		UserAuth userAuth = (UserAuth) auth.getPrincipal();
		
        String token = JWT.create()
                .withSubject(userAuth.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));

        JSONObject json = new JSONObject();	
        json.put("user", userAuth.getUsername());
        json.put("token", token);
        
        res.getWriter().write(json.toString());
        res.getWriter().flush();
    }
}
