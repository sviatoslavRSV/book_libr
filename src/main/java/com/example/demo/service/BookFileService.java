package com.example.demo.service;


import com.example.demo.model.library.ImageFile;


public interface BookFileService {
    void removeImage(int id);

    ImageFile getImage(int id);

    ImageFile saveImage(ImageFile imageFile);
}
