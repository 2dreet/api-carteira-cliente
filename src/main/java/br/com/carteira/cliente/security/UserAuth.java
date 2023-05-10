package br.com.carteira.cliente.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.carteira.cliente.domain.model.User;

@SuppressWarnings("serial")
public class UserAuth implements UserDetails {
	User user;

	UserAuth(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>();
	}

	@Override
	public String getPassword() {
		return user != null ? user.getPassword() : null;
	}

	@Override
	public String getUsername() {
		return user != null ? user.getLogin() : null;
	}
	
	public String getRule() {
		return user != null ? user.getRule() : null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public User getUser() {
		return user;
	}
}
