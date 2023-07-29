package com.blopapi.blopapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	}
	
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
	 return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
		     .csrf().disable()
		     .authorizeRequests()
		     .antMatchers(HttpMethod.GET,"/api/**").permitAll()
		     .antMatchers("/api/auth/**").permitAll()
		     .antMatchers("/api/auth/signout/**").permitAll()
//		     .antMatchers("/api/auth/signup").hasRole("ADMIN")
		     .anyRequest()
		     .authenticated()
		     .and()
		     .httpBasic();
	}
	
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
	UserDetails user=User.builder().username("sameer")
			.password(passwordEncoder().encode("password"))
			.roles("USER").build();
	
	UserDetails admin=User.builder().username("admin")
			.password(passwordEncoder().encode("password"))
			.roles("ADMIN").build();
	
	return new InMemoryUserDetailsManager(user,admin);
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}
