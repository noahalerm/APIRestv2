package com.example.apirestv2.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginPassword {
    //ATTRIBUTES
    private String username;
    private String password;
}
