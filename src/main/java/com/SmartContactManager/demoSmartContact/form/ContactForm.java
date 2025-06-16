package com.SmartContactManager.demoSmartContact.form;

import org.springframework.web.multipart.MultipartFile;

import com.SmartContactManager.demoSmartContact.validators.ValidFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactForm {

    @NotBlank(message ="Name is required")
    private String name;

    @NotBlank(message = "Invalid Email")
    @Email(message = "Invalid Email")
    private String email;

    @NotBlank(message = "Phone No is required")
    @Pattern(regexp = "^[0-9]{10}$", message ="Invalid Phone Number")
    private String phonenumber;

    @NotBlank(message = "Address is required")
    private String address;

    
    private String desc;
    private boolean fav;
    private String websitelink;
    private String linkedin;


    @ValidFile(message = "Invalid File")
    private MultipartFile contactImage;



}
