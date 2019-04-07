package com.stackroute.keepnote.jwtfilter;


import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;



/* This class implements the custom filter by extending org.springframework.web.filter.GenericFilterBean.  
 * Override the doFilter method with ServletRequest, ServletResponse and FilterChain.
 * This is used to authorize the API access for the application.
 */


public class JwtFilter extends GenericFilterBean {

	public static final String AUTHORIZATION = "Authorization";
	private final String secret;

	public JwtFilter(String secret) {
		this.secret = secret;
	}	
	
	

	/*
	 * Override the doFilter method of GenericFilterBean.
     * Retrieve the "authorization" header from the HttpServletRequest object.
     * Retrieve the "Bearer" token from "authorization" header.
     * If authorization header is invalid, throw Exception with message. 
     * Parse the JWT token and get claims from the token using the secret key
     * Set the request attribute with the retrieved claims
     * Call FilterChain object's doFilter() method */
	
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    	HttpServletRequest req = (HttpServletRequest) request;

		// We get the Authorization Header of the incoming request
		String authHeader = req.getHeader(AUTHORIZATION);

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new ServletException("Not a valid authentication authHeader");
		}

		// and retrieve the token
		String compactJws = authHeader.substring(7);

		try {
			Claims token = Jwts.parser().setSigningKey(secret).parseClaimsJws(compactJws).getBody();

			// Here we can extract different properties of the request,
			// such that the user or the issuedAt and make sure that the token
			// is still valid and that the user exists in our user db

			request.setAttribute("token", token);
		} catch (SignatureException ex) {
			throw new ServletException("Invalid Token");
		} catch (MalformedJwtException ex) {
			throw new ServletException("JWT is malformed");
		}

		chain.doFilter(request, response);



    }
}
