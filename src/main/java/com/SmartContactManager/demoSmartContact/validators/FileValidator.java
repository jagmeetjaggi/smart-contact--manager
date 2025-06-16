package com.SmartContactManager.demoSmartContact.validators;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
import java.awt.*;
import java.awt.image.BufferedImage;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import javax.imageio.*;
public class FileValidator implements ConstraintValidator<ValidFile,MultipartFile>{


    private static final long MAX_FILE_SIZE = 1024 * 1024 *2;



    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {



        if(file == null || file.isEmpty())
        {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File cannot be Empty").addConstraintViolation();
            return false;
        }

        System.out.println("file size: "+ file.getSize());
        if(file.getSize() > MAX_FILE_SIZE)
        {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File size should be less 2MB").addConstraintViolation(

            );
            return false;
        
        }
        
        // try
        // {
        //     BufferedImage bufferedImage = ImageIO.read(file.getInputStream());

        //     if(bufferedImage.get)

        // }catch(IOException e)
        // {
        //     e.printStackTrace();
        // }

        return true;
        
    }

}
