package com.example.blogging.blog.controller;


import com.example.blogging.blog.payload.LoginRequest;
import com.example.blogging.blog.payload.LoginResponse;
import com.example.blogging.blog.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<LoginResponse> validate(@RequestBody LoginRequest loginRequest) {
         this.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        UserDetails userDetails= this.userDetailsService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtUtils.generateToken(userDetails);
        LoginResponse response= new LoginResponse();
        response.setToken(token);
        return ResponseEntity.ok(response);
    }

    private void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        authenticationManager.authenticate(authenticationToken);
    }


}
