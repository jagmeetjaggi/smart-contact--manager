package com.SmartContactManager.demoSmartContact.services.imp;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SmartContactManager.demoSmartContact.entity.helper.AppConstant;
import com.SmartContactManager.demoSmartContact.services.ImageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;

@Service
public class ImageServiceImp implements ImageService{


    private Cloudinary cloudinary;

    public ImageServiceImp(Cloudinary cloudinary)
    {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile contactImage, String filename) {


        try
        {
            byte[] data = new byte[contactImage.getInputStream().available()];

            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                "public_id", filename
            ));

            return this.getURLPublicId(filename);

        }catch(IOException e)
        {
            e.printStackTrace();
            return null;
        }      

        
        
    }

    @Override
    public String getURLPublicId(String publicId) {
        
        return cloudinary
        .url()
        .transformation(
            new Transformation<>()
            .width(AppConstant.CONTACT_IMAGE_WIDTH)
            .height(AppConstant.CONTACT_IMAGE_HEIGHT)
            .crop(AppConstant.CONTACT_IMAGE_CROP)
        )
        .generate(publicId);
    }



}
