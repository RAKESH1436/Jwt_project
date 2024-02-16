package com.example.demo.controller;

import com.example.demo.helper.JwtHelper;
import com.example.demo.model.JwtRequest;
import com.example.demo.model.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CachingUserDetailsService cachingUserDetailsService;
    @Autowired
    private JwtHelper jwtHelper;

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        System.out.println(jwtRequest);

        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));


        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            throw new Exception("bad credenting");

        }

        UserDetails userDetails = this.cachingUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtHelper.generateToken(userDetails);
        System.out.println("JWT" + token);
        return ResponseEntity.ok(new JwtResponse(token));



    }}