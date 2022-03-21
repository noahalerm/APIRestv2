package com.example.apirestv2.security.jwt;

import com.example.apirestv2.model.entities.UserDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserJWT extends UserDTO {
    //ATTRIBUTES
    private String token;

    //CONSTRUCTOR
    @Builder(builderMethodName = "jwtUsuariJwtBuilder")
    public UserJWT(String username, String avatar, String role, String token) {
        super(username, avatar, role);

        this.token = token;
    }
}
