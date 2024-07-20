package com.shopme.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.servlet.configuration.WebMvcSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
	auth.authenticationProvider(authenticationProvider());
	}
	
	@Bean
	protected void configure(HttpSecurity http) throws Exception{
		http.authenticationProvider(authenticationProvider());
		
		http.authorizeHttpRequests(auth -> 
		auth.anyRequest().authenticated()).
		formLogin(form -> form.loginPage
				("/login").usernameParameter("email")
				.permitAll() );
	}
	
	
	@Bean
	private DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(PasswordEncoder());
		return authProvider;
	}

@Bean
	private UserDetailsService userDetailsService() {
		return new ShopMeUserDetailsService();
	}


	@Bean
	public PasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	WebSecurityCustomizer configureWebSecurity() throws Exception{
		return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
		
	}
}
