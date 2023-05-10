package br.com.carteira.cliente.security.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.carteira.cliente.constants.SecurityConstants;
import br.com.carteira.cliente.request.UserAuthRequest;
import br.com.carteira.cliente.response.RestError;
import br.com.carteira.cliente.security.UserAuth;
import br.com.carteira.cliente.util.AppUtil;
import br.com.carteira.cliente.util.ReponseUtil;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	AuthenticationManager authenticationManager;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		setFilterProcessesUrl("/user/auth");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
		try {
			UserAuthRequest creds = new ObjectMapper().readValue(req.getInputStream(), UserAuthRequest.class);

			res.setContentType("application/json");
			Authentication auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(creds.getLogin(), creds.getPassword(), new ArrayList<>()));
			
			UserAuth user = (UserAuth) auth.getPrincipal();
			if(user == null || user.getUser() == null || user.getUser().getCompany() == null) {
				ReponseUtil.sendResponseErroAuthenticate(req, res, new RestError(HttpStatus.BAD_REQUEST.value(), "Credenciais invalídas"));
			} else if (!user.getUser().getCompany().getCnpj().equals(AppUtil.onlyNumber(creds.getCnpj()))) {
				ReponseUtil.sendResponseErroAuthenticate(req, res, new RestError(HttpStatus.BAD_REQUEST.value(), "Credenciais invalídas"));
			}
			
			return auth;
		} catch (Exception e) {
			
		}
		
		try {
			ReponseUtil.sendResponseErroAuthenticate(req, res, new RestError(HttpStatus.BAD_REQUEST.value(), "Credenciais invalídas"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
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
        json.put("rule", userAuth.getRule());
        
        res.getWriter().write(json.toString());
        res.getWriter().flush();
    }
}
