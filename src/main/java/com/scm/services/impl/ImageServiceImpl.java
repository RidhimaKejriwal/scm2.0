package com.scm.services.impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService{

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile contactImage) {
        // upload image and return url

        String filename = UUID.randomUUID().toString();

        try {

            byte[] data = new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                "public_id", contactImage.getOriginalFilename()
            ));

            return this.getUrlFromPublicId(filename);
        
        } catch (IOException e) {
            
            e.printStackTrace();
            return null;
        }
        
    }

    @Override
    public String getUrlFromPublicId(String publicId) {
        
        return cloudinary
        .url()
        .transformation(
            new Transformation<>()
            .width(500)
            .height(500)
            .crop("fill")
        )
        .generate(publicId);
    }
    
}
