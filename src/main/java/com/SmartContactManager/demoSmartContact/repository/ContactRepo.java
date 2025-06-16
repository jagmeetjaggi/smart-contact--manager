package com.SmartContactManager.demoSmartContact.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.SmartContactManager.demoSmartContact.entity.Contact;
import com.SmartContactManager.demoSmartContact.entity.User1;


@Repository
public interface ContactRepo extends JpaRepository<Contact,String>{

    //find the contact bu user
    Page<Contact> findByUser1(User1 user1, Pageable pageable);

    List<Contact> findByUser1(User1 user1); // ✅ Correct field name

    @Query("SELECT c FROM Contact c WHERE c.user1.id = :userId")
    List<Contact> findByUserId(@Param("userId") String userId);
}
