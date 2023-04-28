package com.example.elbekjonumarovpaymenttask.controller;

import com.example.elbekjonumarovpaymenttask.config.jwt.JwtProvider;
import com.example.elbekjonumarovpaymenttask.payload.LoginDto;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.Binding;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDto, BindingResult result) {
        if (result.hasErrors())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            try {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
                Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authenticate);
                String generatedToken = jwtProvider.generateToken(authenticate);
                return new ResponseEntity<>(generatedToken, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("password or username is not correct", HttpStatus.UNAUTHORIZED);
            }
        }
    }
