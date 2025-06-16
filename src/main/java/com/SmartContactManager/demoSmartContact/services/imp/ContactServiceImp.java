package com.SmartContactManager.demoSmartContact.services.imp;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.SmartContactManager.demoSmartContact.entity.Contact;
import com.SmartContactManager.demoSmartContact.entity.User1;
import com.SmartContactManager.demoSmartContact.entity.helper.ResourceNotFoundException;
import com.SmartContactManager.demoSmartContact.repository.ContactRepo;
import com.SmartContactManager.demoSmartContact.services.ContactService;

import lombok.experimental.var;

@Service
public class ContactServiceImp implements ContactService{


    @Autowired
    private ContactRepo contactrepo;


    @Override
    public Contact save(Contact contact) {
        String contactid = UUID.randomUUID().toString();
        contact.setId(contactid);
        return contactrepo.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        return null;

    }

    @Override
    public List<Contact> getAll() {
        return contactrepo.findAll();
    }

    @Override
    public Contact getById(String id) {
        return contactrepo.findById(id)
        .orElseThrow(()-> new ResourceNotFoundException("contact not found with given id  "+ id));
    }

    @Override
    public void delete(String id) {

        var contact = contactrepo.findById(id)
        .orElseThrow(()-> new ResourceNotFoundException("contact not found with given id  "+ id));

       contactrepo.delete(contact);
    }

    @Override
    public List<Contact> search(String name, String email, String phonenumber) {
        return null;
    }

    @Override
    public List<Contact> getByUserId(String userid) {
        return contactrepo.findByUserId(userid);
    }

    @Override
    public Page<Contact> getByUser(User1 user,int page, int size, String sortBy, String direction) {

    Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        lombok.var pageable = PageRequest.of(page,size);

        return contactrepo.findByUser1(user, pageable);
    }

}
