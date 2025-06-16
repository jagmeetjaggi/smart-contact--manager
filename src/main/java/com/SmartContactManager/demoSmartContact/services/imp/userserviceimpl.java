package com.SmartContactManager.demoSmartContact.services.imp;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.SmartContactManager.demoSmartContact.entity.User1;
import com.SmartContactManager.demoSmartContact.entity.helper.AppConstant;
import com.SmartContactManager.demoSmartContact.entity.helper.ResourceNotFoundException;
import com.SmartContactManager.demoSmartContact.repository.userrepo;
import com.SmartContactManager.demoSmartContact.services.userservice;

@Service
public class userserviceimpl implements userservice{


    @Autowired
    private userrepo repo;

    @Autowired
    private PasswordEncoder passwordencoder;

    @Override
    public User1 saveUser(User1 user) {

        String userid = UUID.randomUUID().toString();
        user.setUserid(userid);

        user.setPassword(passwordencoder.encode(user.getPassword()));
        user.setRolelist(List.of(AppConstant.ROLE_USER));
        
        return repo.save(user);
    }

    @Override
    public Optional<User1> getUserById(String id) {

        return repo.findById(id);

    }

    @Override
    public Optional<User1> updateUser1(User1 user) {
        // Find the user by ID or throw an exception if not found
        User1 uu = repo.findById(user.getUserid())
                       .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    
        // Update the user with new values from the `user` parameter
        uu.setName(user.getName());
        uu.setEmail(user.getEmail());
        uu.setPassword(user.getPassword());
        uu.setAbout(user.getAbout());
        uu.setPhoneNumber(user.getPhoneNumber());
        uu.setProfilepic(user.getProfilepic());
        uu.setEnable(user.isEnabled());
        uu.setEmailverification(user.isEmailverification());
        uu.setPhoneverified(user.isPhoneverified());
        uu.setPROV(user.getPROV());
        uu.setProvideruserid(user.getProvideruserid());


       User1 save =  repo.save(uu);
       return Optional.ofNullable(save);
    }
    
    @Override
    public void deletUser1(String id) {
        User1 uu = repo.findById(id)
                       .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    
         repo.delete(uu);
        
    }

    @Override
    public boolean isUserExist(String userid) {
        User1 uu = repo.findById(userid).orElse(null);
    
        return uu != null ? true : false;
        // Updat
    }

    @Override
    public boolean isUserExistByemail(String email) {
        User1 user = repo.findByEmail(email).orElse(null);
        return user!=null ? true:false;
    }

    @Override
    public List<User1> getAllUser() {
        return repo.findAll();
    }

    @Override
    public User1 getUserbyemail(String email) {
       
       
        return repo.findByEmail(email).orElse(null);
    }

}
