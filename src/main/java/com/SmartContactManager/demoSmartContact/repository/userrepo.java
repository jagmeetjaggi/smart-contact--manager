package com.SmartContactManager.demoSmartContact.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SmartContactManager.demoSmartContact.entity.User1;
@Repository
public interface userrepo extends JpaRepository<User1, String>{
    

    Optional<User1> findByEmail(String email);
    Optional<User1> findByEmailAndPassword(String email, String password);

}
