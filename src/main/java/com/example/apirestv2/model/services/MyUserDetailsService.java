package com.example.apirestv2.model.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    //ATTRIBUTES
    private final UserService userService;

    //METHODS
    /**
     * This method is used to load a user by their username.
     * @param username Username
     * @return User Details
     * @throws UsernameNotFoundException Not Found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.consultByUsername(username);
    }

    /**
     * This method is used to load a user by their ID.
     * @param id User's ID
     * @return User Details
     */
    public UserDetails loadUserById(Long id) {
        return userService.consultById(id);
    }
}
