package org.java.lessons.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) 
  	throws Exception {
    http.authorizeRequests()
        .antMatchers("/books/create","/books/edit").hasAuthority("ADMIN")
        .antMatchers(HttpMethod.POST, "/books/**").hasAuthority("ADMIN")
        .antMatchers("/categories", "/categories/**").hasAuthority("ADMIN")
        .antMatchers("/books","/books/**").hasAnyAuthority("USER", "ADMIN")
        .antMatchers("/**").permitAll()
        .and().formLogin()
        .and().logout();
    return http.build();
  }
  
  @Bean
  DatabaseUserDetailsService userDetailsService() {
    return new DatabaseUserDetailsService();
  }
  
  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }  
  
  @Bean
  DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
 
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
 
    return authProvider;
  }  

}