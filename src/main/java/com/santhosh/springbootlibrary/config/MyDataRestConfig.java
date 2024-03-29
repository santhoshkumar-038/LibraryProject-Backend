package com.santhosh.springbootlibrary.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;


import com.santhosh.springbootlibrary.entity.Book;
import com.santhosh.springbootlibrary.entity.Review;
import com.santhosh.springbootlibrary.entity.Message;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

	private String theAllowedOrigins = "https://localhost:3000";

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		HttpMethod[] theUnsupportedActions = { HttpMethod.PUT, HttpMethod.POST, HttpMethod.PATCH, HttpMethod.DELETE };
		config.exposeIdsFor(Book.class);
		config.exposeIdsFor(Review.class);
		config.exposeIdsFor(Message.class);
		
		disableHttpMethods(Book.class, config, theUnsupportedActions);
		disableHttpMethods(Review.class, config, theUnsupportedActions);
		disableHttpMethods(Message.class, config, theUnsupportedActions);
		
		/* Configure CORS Mapping*/
		cors.addMapping(config.getBasePath() + "/**").allowedOrigins(theAllowedOrigins);
	}
	
	public void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
		config.getExposureConfiguration()
			  .forDomainType(theClass)
			  .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
			  .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
	}
}
