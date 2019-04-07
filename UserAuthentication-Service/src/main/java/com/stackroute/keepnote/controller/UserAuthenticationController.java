package com.stackroute.keepnote.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exception.UserAlreadyExistsException;
import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.service.UserAuthenticationService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

/*
 * As in this assignment, we are working on creating RESTful web service, hence annotate
 * the class with @RestController annotation. A class annotated with the @Controller annotation
 * has handler methods which return a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized
 * format. Starting from Spring 4 and above, we can use @RestController annotation which
 * is equivalent to using @Controller and @ResposeBody annotation
 */

//@FeignClient(name = "UserAuthenticationController")
///@RibbonClient(name = "UserAuthenticationController")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserAuthenticationController {

	/*
	 * Autowiring should be implemented for the UserAuthenticationService. (Use
	 * Constructor-based autowiring) Please note that we should not create an
	 * object using the new keyword
	 */
	@Autowired
	private UserAuthenticationService authicationService;
	private String SECRET = "SecretKeyToGenJWTs";
	public UserAuthenticationController(UserAuthenticationService authicationService) {
		this.authicationService = authicationService;
	}

	/*
	 * Define a handler method which will create a specific user by reading the
	 * Serialized object from request body and save the user details in the
	 * database. This handler method should return any one of the status
	 * messages basis on different situations: 1. 201(CREATED) - If the user
	 * created successfully. 2. 409(CONFLICT) - If the userId conflicts with any
	 * existing user
	 *
	 * This handler method should map to the URL "/api/v1/auth/register" using
	 * HTTP POST method
	 */

	@PostMapping(value = "/api/v1/auth/register")
	public ResponseEntity<User> registerUser(@RequestBody User user) {

		try {
			this.authicationService.saveUser(user);
		} catch (UserAlreadyExistsException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<User>(HttpStatus.CONFLICT);
			// e.printStackTrace();
		}
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

	/*
	 * Define a handler method which will authenticate a user by reading the
	 * Serialized user object from request body containing the username and
	 * password. The username and password should be validated before proceeding
	 * ahead with JWT token generation. The user credentials will be validated
	 * against the database entries. The error should be return if validation is
	 * not successful. If credentials are validated successfully, then JWT token
	 * will be generated. The token should be returned back to the caller along
	 * with the API response. This handler method should return any one of the
	 * status messages basis on different situations: 1. 200(OK) - If login is
	 * successful 2. 401(UNAUTHORIZED) - If login is not successful
	 *
	 * This handler method should map to the URL "/api/v1/auth/login" using HTTP
	 * POST method
	 */
	@PostMapping(value="/api/v1/auth/login")
	public ResponseEntity<User> getUser(@RequestBody User user) {
		User returnuser = null;
		try {
            System.out.println(user.getUserId()+"\t"+ user.getUserPassword()+"Check the details");
			returnuser = this.authicationService.findByUserIdAndPassword(user.getUserId(), user.getUserPassword());
			if (returnuser != null) {

				String token;
				try {
					token = this.getToken(user.getUserId(), user.getUserPassword());
					returnuser.setToken(token);
					return new ResponseEntity<User>(returnuser, HttpStatus.OK);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
				}

			} else {
				return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
			}
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
		}

	}
	@PostMapping(value="/api/v1/auth/isAuthenticated", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Map<String,Boolean>>
	isAuthenticated(@RequestHeader(name = "Authorization", required = false) String authHeader) {
    	//HttpServletRequest req = (HttpServletRequest) request;

		// We get the Authorization Header of the incoming request
		//authHeader = headers.getRequestHeader("Authorization").get(0);
        System.out.println(authHeader+"check");
        Map<String,Boolean>  responsemap=new HashMap<>();
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			System.out.println("Bearer");
			responsemap.put("isAuthenticated",true);
			return new ResponseEntity<Map<String,Boolean>>(responsemap,HttpStatus.OK);
		}

		// and retrieve the token
		String compactJws = authHeader.substring(7);
        System.out.println(compactJws);
		try {
			Claims token = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(compactJws).getBody();

			// Here we can extract different properties of the request,
			// such that the user or the issuedAt and make sure that the token
			// is still valid and that the user exists in our user db
			if(token!=null){
				responsemap.put("isAuthenticated",true);
			return new ResponseEntity<Map<String,Boolean>>(responsemap, HttpStatus.OK);
			}else{
				System.out.println("Bearer1");
				responsemap.put("isAuthenticated",false);
				return new ResponseEntity<Map<String,Boolean>>(responsemap,HttpStatus.UNAUTHORIZED);
			}

			//request.setAttribute("token", token);
		} catch (SignatureException ex) {
			System.out.println("Invalid Token");
			responsemap.put("isAuthenticated",false);
			return new ResponseEntity<Map<String,Boolean>>(responsemap,HttpStatus.UNAUTHORIZED);
		} catch (MalformedJwtException ex) {
			System.out.println("Invalid Token1");
			responsemap.put("isAuthenticated",false);
			return new ResponseEntity<Map<String,Boolean>>(responsemap,HttpStatus.UNAUTHORIZED);
		}

	}
	// Generate JWT token
	public String getToken(String username, String password) throws Exception {

		long EXPIRATION_TIME = 864_000_000; // 10 days
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("UserId", username + "");
		claims.put("password", password);

		return Jwts.builder().setClaims(claims).setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();

	}

}
