package com.SmartContactManager.demoSmartContact.services.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.SmartContactManager.demoSmartContact.entity.User1;
import com.SmartContactManager.demoSmartContact.repository.userrepo;


@Service
public class SecurityCustomUserDetail implements UserDetailsService{


    @Autowired
    private userrepo repo;

   @Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User1 user = repo.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

    return new org.springframework.security.core.userdetails.User(
            user.getEmail(), // Username
            user.getPassword(), // Password
            user.getAuthorities() // Authorities (roles)
    );
}

}