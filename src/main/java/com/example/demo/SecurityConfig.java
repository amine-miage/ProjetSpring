package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter{
    @Autowired
	public void globalConfig(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("123").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("employe").password("123").roles("EMPLOYE");
		auth.inMemoryAuthentication().withUser("client").password("123").roles("CLIENT");
	}
	
 @Override
 protected void configure(HttpSecurity http) throws Exception{
	 http
	 .csrf().disable()
	     .authorizeRequests()
	     .antMatchers(
                 "/",
                 "/js/**",
                 "/css/**",
                 "/img/**",
                 "/webjars/**").permitAll()
	                 .anyRequest()
	                     .authenticated()
	                        .and()
	            .formLogin()
	                  .loginPage("/login")
	                  .permitAll()
	                  .defaultSuccessUrl("/accueil")
	                  .failureUrl("/error")
	                   .and() 
	             . logout()
	                        .invalidateHttpSession(true)
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/login")
                            .permitAll();	                             
 }
 
}
