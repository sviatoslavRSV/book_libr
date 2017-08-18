package com.example.demo.service;


import com.example.demo.model.library.BookFile;
import com.example.demo.model.library.ImageFile;


public interface BookFileService {
    BookFile getBookFile(String fileName);

    ImageFile getImageFile(String imageName);

    ImageFile getImage(int id);

    ImageFile saveImage(ImageFile imageFile);

    ImageFile updateImage(ImageFile imageFile);
}
