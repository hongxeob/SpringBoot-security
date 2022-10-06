package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨.
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable();
//		http.authorizeRequests()
//		.antMatchers("/user/**").authenticated() //로그인 한사람만 가능
//		.antMatchers("/manager/**").access("hasROLE('ROLE_ADMIN') or hasROLE('ROLE_MANAGER')") //로그인 했지만 '매니저'라는 권한 있어야 접속가능.
//		.antMatchers("/admin/**").access("hasROLE('ROLE_ADMIN')") //로그인 했지만 '어드민'이라는 권한 있어야 접속가능.
//		.anyRequest().permitAll(); //위 세개 주소가 아닌 경우 권한 허용.
//
//	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/user/**").authenticated()//로그인 한사람만 가능
			.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")//로그인 했지만 '매니저'라는 권한 있어야 접속가능.
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") //로그인 했지만 '어드민'이라는 권한 있어야 접속가능.
			.anyRequest().permitAll() //위 세개 주소가 아닌 경우 권한 허용.
			.and()
			.formLogin()
			.loginPage("/login");
		
	}
}
 