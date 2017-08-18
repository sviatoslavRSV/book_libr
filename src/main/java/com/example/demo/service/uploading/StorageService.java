package com.example.demo.service.uploading;

import com.example.demo.model.library.ImageFile;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void store(MultipartFile file, ImageFile imageFile);

    Resource loadFileAsResource(String filename);

//    MultipartFile getMultipartFile();
}
