package com.example.elbekjonumarovpaymenttask.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<User> users = new ArrayList<>(
                Arrays.asList(
                        new User("Peter", "code", new ArrayList<>()),
                        new User("John", "code", new ArrayList<>()),
                        new User("Devid", "code", new ArrayList<>())
                ));
        for (User user : users) {
            if (user.getUsername().equals(username)){
                return user;
            }
                throw new UsernameNotFoundException("user not found here");
        }
        return null;
    }
}
