package com.SmartContactManager.demoSmartContact.services;

import com.SmartContactManager.demoSmartContact.entity.User1;
import com.SmartContactManager.demoSmartContact.repository.userrepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final userrepo userRepository;

    public UserDetailsServiceImpl(userrepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User1> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        User1 user = userOptional.get();

        // Convert role list (Strings) to GrantedAuthority objects
        Set<GrantedAuthority> authorities = user.getRolelist().stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // Ensure prefix is added
        .collect(Collectors.toSet());


        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword() != null ? user.getPassword() : "{noop}defaultPassword") // Handle OAuth users
                .authorities(authorities)
                .disabled(!user.isEnabled()) // Ensure the method exists in User1 class
                .build();
    }
}
