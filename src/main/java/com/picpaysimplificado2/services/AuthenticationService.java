package com.picpaysimplificado2.services;

import com.picpaysimplificado2.domain.auth.AuthenticationDTO;
import com.picpaysimplificado2.domain.auth.AuthenticationResponse;
import com.picpaysimplificado2.domain.user.User;
import com.picpaysimplificado2.dtos.UserDTO;
import com.picpaysimplificado2.infra.JwtService;
import com.picpaysimplificado2.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    public AuthenticationResponse register(UserDTO request){
        var user = User.builder()
                .userType(request.getUserType())
                .balance(request.getBalance())
                .SSN(request.getSSN())
                .email(request.getEmail())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse authenticate(AuthenticationDTO request){
        var userPassword = new UsernamePasswordAuthenticationToken(request.login(),request.senha());
        var auth = authenticationManager.authenticate(userPassword);
        UserDetails user = userRepository.findUserByEmail(request.login()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
