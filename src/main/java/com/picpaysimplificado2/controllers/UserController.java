package com.picpaysimplificado2.controllers;

import com.picpaysimplificado2.dtos.UserDTO;
import com.picpaysimplificado2.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO user){
        UserDTO newUser = this.userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        UserDTO user = this.userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> listUsers(){
        return ResponseEntity.ok(this.userService.listUsers());
    }
}
