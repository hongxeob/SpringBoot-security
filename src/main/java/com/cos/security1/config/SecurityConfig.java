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
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true) //secure 어노테이션 활성화 
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	//해당 메소드의 리턴되는 오브젝트를 IoC로 등록해준다.
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/user/**").authenticated()//로그인 한사람만 가능
			.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")//로그인 했지만 '매니저'라는 권한 있어야 접속가능.
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") //로그인 했지만 '어드민'이라는 권한 있어야 접속가능.
			.anyRequest().permitAll() //위 세개 주소가 아닌 경우 권한 허용.
			.and()
			.formLogin()
			.loginPage("/loginForm")
			.loginProcessingUrl("/login") //login 주소가 호출되면 시큐리티가 낚아채서 대신 로그인 진행 해준다.
			.defaultSuccessUrl("/");
			
		
	}
}
 