package com.picpaysimplificado2.controllers;

import com.picpaysimplificado2.domain.auth.AuthenticationDTO;
import com.picpaysimplificado2.dtos.UserDTO;
import com.picpaysimplificado2.services.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserDTO user){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.register(user));
    }
    @PostMapping("/authenticate")
    public ResponseEntity authenticate(@RequestBody AuthenticationDTO auth){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.authenticate(auth));
    }

}
