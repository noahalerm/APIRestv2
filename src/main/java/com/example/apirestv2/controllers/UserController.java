package com.example.apirestv2.controllers;

import com.example.apirestv2.model.entities.User;
import com.example.apirestv2.model.entities.UserDTO;
import com.example.apirestv2.model.services.UserService;
import com.example.apirestv2.security.jwt.JwtProvider;
import com.example.apirestv2.security.jwt.LoginPassword;
import com.example.apirestv2.security.jwt.UserJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    //ATTRIBUTES
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider tokenProvider;

    //METHODS

    //GET
    @GetMapping("/login")
    public UserDTO login(@AuthenticationPrincipal User user){
        return new UserDTO(user.getUsername(), user.getAvatar(), user.getRole());
    }

    //GET
    @GetMapping("/users")
    public ResponseEntity<?> listUsersDTO() {
        //LISTS
        List<User> res = userService.listUsers();
        List<UserDTO> aux = new ArrayList();

        //INSERT LOOP
        for (User user : res) {
            aux.add(new UserDTO(user.getUsername(), user.getAvatar(), user.getRole()));
        }

        //OUTPUT
        if (res == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(aux);
    }

    //POST
    @PostMapping("/users")
    public ResponseEntity<?> createNewUser (@RequestBody User newUser) {
        try {
            User res = userService.createNewUser(newUser);
            UserDTO user = new UserDTO(res.getUsername(), res.getAvatar(), res.getRole());

            return new ResponseEntity(user, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    //POST
    @PostMapping("/login")
    public ResponseEntity<UserJWT> login(@RequestBody LoginPassword userPassword) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userPassword.getUsername(), userPassword.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);
        User user = (User) auth.getPrincipal();
        String token = tokenProvider.generateToken(auth);
        UserJWT userJWT = new UserJWT(user.getUsername(), user.getAvatar(), user.getRole(), token);

        return ResponseEntity.status(HttpStatus.CREATED).body(userJWT);
    }

    /*
    {
        "username":"Noah",
        "password":"1234",
        "avatar":"noah.png"
    }
    */
}
