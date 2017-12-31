package com.example.demo;

import javax.sql.DataSource;

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
	public void globalConfig(AuthenticationManagerBuilder auth,DataSource datasource) throws Exception {
    	/*
		auth.inMemoryAuthentication().withUser("admin").password("123").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("employe").password("123").roles("EMPLOYE");
		auth.inMemoryAuthentication().withUser("client").password("123").roles("CLIENT");
		*/
    	auth.jdbcAuthentication()
    	    .dataSource(datasource)
    	    .usersByUsernameQuery("select mail as principal, password as credentials,true from user  where mail = ? AND active ='1' ")
    	    .authoritiesByUsernameQuery("select mail as principal ,role as role  from user where mail = ?")
    	    .rolePrefix("ROLE_");
    	    
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
                 "/webjars/**","/accueil","/accueil/inscription","/**").permitAll()
	                 .anyRequest()
	                     .authenticated()
	                        .and()
	            .formLogin()
	                  .loginPage("/login")
	                  .permitAll()
	                  .defaultSuccessUrl("/authentification")
	                  .failureUrl("/error")
	                   .and() 
	             . logout()
	                        .invalidateHttpSession(true)
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/login")
                            .permitAll();	                             
 }
 
}
