package com.SmartContactManager.demoSmartContact.entity.helper;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String msg)
    {
        super(msg);
    }

    public ResourceNotFoundException()
    {
        super("resource not found");
    }
}
