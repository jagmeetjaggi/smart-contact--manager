package com.SmartContactManager.demoSmartContact.services;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

import com.SmartContactManager.demoSmartContact.entity.User1;

import java.util.*;

public interface userservice {
    User1 saveUser(User1 user);
    
    Optional<User1> getUserById(String id);
    Optional<User1> updateUser1(User1 user);
    void deletUser1(String id);
    boolean isUserExist(String userid);
    boolean isUserExistByemail(String email);
    List<User1> getAllUser();
    User1 getUserbyemail(String email);
}
