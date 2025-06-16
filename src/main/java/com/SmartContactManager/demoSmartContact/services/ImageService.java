package com.SmartContactManager.demoSmartContact.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String uploadImage(MultipartFile contactImage, String filename);

    String getURLPublicId(String publicId);
}
