package com.stackroute.keepnote;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.stackroute.keepnote.jwtfilter.JwtFilter;

/*
 * The @SpringBootApplication annotation is equivalent to using @Configuration, @EnableAutoConfiguration 
 * and @ComponentScan with their default attribu
 * tes
 */
@EnableDiscoveryClient
//@EnableFeignClients
@SpringBootApplication
public class NoteServiceApplication {

	
	
	/*
	 * Define the bean for Filter registration. Create a new FilterRegistrationBean
	 * object and use setFilter() method to set new instance of JwtFilter object.
	 * Also specifies the Url patterns for registration bean.
	 */
	 /* @Bean
	    public FilterRegistrationBean<Filter> jwtFilter() {
	       FilterRegistrationBean<Filter> bean=new FilterRegistrationBean<Filter>();
	       bean.setFilter(new JwtFilter("SecretKeyToGenJWTs"));
	            return bean ;
	    }*/
/*	
	  @Bean
	  public RestTemplate restTemplate(RestTemplateBuilder builder) {
	   return builder.build();
	  }*/
	
	/*
	 * 
	 * You need to run SpringApplication.run, because this method start whole spring
	 * framework. Code below integrates your main() with SpringBoot
	 */

	public static void main(String[] args) {
		SpringApplication.run(NoteServiceApplication.class, args);
	}
}
