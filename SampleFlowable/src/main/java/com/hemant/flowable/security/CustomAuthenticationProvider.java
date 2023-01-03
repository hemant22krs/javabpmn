package com.hemant.flowable.security;

import java.util.List;
import java.util.stream.Collectors;

import org.flowable.engine.IdentityService;
import org.flowable.idm.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private IdentityService identityService;
	
	//@Autowired
	//private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = identityService.createUserQuery().userIdIgnoreCase(username.toLowerCase()).singleResult();
        if(user == null) {
        	throw new BadCredentialsException("Invalid username");
        }
        if(password.equalsIgnoreCase(user.getPassword())) {
        	List<GrantedAuthority> authorities = identityService.createGroupQuery().groupMember(user.getId()).list().stream().map(group -> new SimpleGrantedAuthority(group.getId())).collect(Collectors.toList());
        	return new UsernamePasswordAuthenticationToken(username, password, authorities);
        }else {
        	throw new BadCredentialsException("Invalid Password");
        }
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
