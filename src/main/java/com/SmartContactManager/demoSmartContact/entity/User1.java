package com.SmartContactManager.demoSmartContact.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

import org.checkerframework.checker.units.qual.C;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.stream.Collectors;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User1 implements UserDetails {

    @Id
    private String userid;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String password;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String profilepic;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String about;

    @Column
    private String phoneNumber;

    @Getter(value = AccessLevel.NONE)
    private boolean enable = true;
    private boolean emailverification = false;
    private boolean phoneverified = false;

    @Enumerated(value = EnumType.STRING)
    private provider PROV = provider.SELF;
    private String provideruserid;

    @OneToMany(mappedBy = "user1", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> rolelist = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rolelist.stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.email; // email is username.
    }

    @Override
    public boolean isEnabled() {
        return this.enable;
    }
}