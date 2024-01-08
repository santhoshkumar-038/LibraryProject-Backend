package com.santhosh.springbootlibrary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

import com.okta.spring.boot.oauth.Okta;

@Configuration
public class SecurityConfigurations{
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		//Disable cross site request forgery
		http.csrf().disable();
        http.authorizeRequests(configurer -> configurer.antMatchers("/api/books/secure/**", 
        															"/api/reviews/secure/**", 
        															"/api/messages/secure/**",
        															"/api/admin/secure/**")
        		.authenticated()).oauth2ResourceServer(server -> server.jwt());
		
        //add cors filter
        http.cors();
        
        //add content to negotiation strategy
        http.setSharedObject(ContentNegotiationStrategy.class, new HeaderContentNegotiationStrategy());
        
        //force a non-empty resource body for 401's to make the response friendly
        Okta.configureResourceServer401ResponseBody(http);
        return http.build();
	}
}
