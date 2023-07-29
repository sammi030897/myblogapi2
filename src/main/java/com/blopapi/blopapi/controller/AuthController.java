package com.blopapi.blopapi.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.blopapi.blopapi.entity.Role;
import com.blopapi.blopapi.entity.User;
import com.blopapi.blopapi.payload.LoginDto;
import com.blopapi.blopapi.payload.SignUpDto;
import com.blopapi.blopapi.repository.RoleRepository;
import com.blopapi.blopapi.repository.UserRepository;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private UserRepository userrepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository rolerepo;
	
	@Autowired
	private AuthenticationManager authrepo;
	
//	http://localhost:8080/api/auth
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
		
		if(userrepo.existsByUsername(signUpDto.getUsername())) {
			return new ResponseEntity<>("UserName is already taken!!",HttpStatus.BAD_REQUEST);
		}
		
		if(userrepo.existsByEmail(signUpDto.getEmail())) {
			return new ResponseEntity<>("Email is already taken!!",HttpStatus.BAD_REQUEST);
		}
		
		User user = new User();
		user.setName(signUpDto.getName());
		user.setUsername(signUpDto.getUsername());
		user.setEmail(signUpDto.getEmail());
		user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
	
		Role role = rolerepo.findByName("ROLE_ADMIN").get();
		user.setRoles(Collections.singleton(role));
		
		userrepo.save(user);
		
		return new ResponseEntity<>("User register successfully",HttpStatus.OK);
	}
	
//	http://localhost:8080/api/auth/signin
	@PostMapping("/signin")
	public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
		Authentication authentication = authrepo.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginDto.getUsernameOrEmail(), loginDto.getPassword()));
	
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseEntity<>("user sign-in successfully",HttpStatus.OK);
	}
    @PostMapping("/signout")
    public ResponseEntity<Map<String,String>> logout(HttpServletRequest request) {
    	SecurityContextHolder.clearContext();
    	
    	Map<String, String> response=new HashMap<>();
    	response.put("message", "logout successful");
    	
        return  ResponseEntity.ok(response);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return new ResponseEntity<>("Logged out successfully",HttpStatus.OK);
    }
}
