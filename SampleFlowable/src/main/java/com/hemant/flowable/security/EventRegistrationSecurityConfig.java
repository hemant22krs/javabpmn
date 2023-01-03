package com.hemant.flowable.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class EventRegistrationSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;

	 @Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
		.antMatchers("/organiser/**,/manager/**").authenticated()
		.anyRequest().permitAll()
		.and()
		.httpBasic()
		.and()
		.formLogin();
	}
	
	  @Override
	  protected void configure(AuthenticationManagerBuilder auth) {
	      auth.authenticationProvider(customAuthenticationProvider);
	  }
	  
	  @Bean
		public PasswordEncoder passwordEncoder () {
			return NoOpPasswordEncoder.getInstance();
		}
}
