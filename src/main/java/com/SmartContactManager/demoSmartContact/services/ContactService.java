package com.SmartContactManager.demoSmartContact.services;

import com.SmartContactManager.demoSmartContact.entity.Contact;
import com.SmartContactManager.demoSmartContact.entity.User1;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactService {
// save contact
        Contact save(Contact contact);

        Contact update(Contact contact);

        List<Contact> getAll();

        Contact getById(String id);

        void delete(String id);

        List<Contact> search(String name, String email, String phonenumber);

        List<Contact> getByUserId(String userid);

        Page<Contact> getByUser(User1 user, int page, int size, String sortField, String sortDirection);
}
