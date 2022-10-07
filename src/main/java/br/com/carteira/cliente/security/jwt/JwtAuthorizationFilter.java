package br.com.carteira.cliente.security.jwt;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.carteira.cliente.constants.SecurityConstants;
import br.com.carteira.cliente.response.RestError;
import br.com.carteira.cliente.util.ReponseUtil;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException, BadCredentialsException {
		String header = req.getHeader(SecurityConstants.HEADER_STRING);

		if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}

		try {
			UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(req, res);
		} catch (Exception e) {
			e.printStackTrace();
			SecurityContextHolder.clearContext();
			try {
				ReponseUtil.sendResponseErroAuthenticate(req, res, new RestError(HttpStatus.FORBIDDEN.value(), "Token não está valído"));
			} catch (IOException ex) {
				e.printStackTrace();
			}
		}
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request)
			throws BadCredentialsException {
		try {
			String token = request.getHeader(SecurityConstants.HEADER_STRING);

			if (token != null) {
				String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes())).build()
						.verify(token.replace(SecurityConstants.TOKEN_PREFIX, "")).getSubject();
				if (user != null) {
					return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		throw new BadCredentialsException("Token inválido.");
	}
}
