package com.example.demo.service;


import com.example.demo.model.library.ImageFile;
import com.example.demo.repository.ImageFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookFileServiceImpl implements BookFileService {
    @Autowired
    private ImageFileRepository imageFileRepository;

    @Override
    public ImageFile getImage(int id) {
        return imageFileRepository.findOne(id);
    }

    @Override
    public void removeImage(int id) {
        ImageFile imageFile = getImage(id);
        imageFileRepository.delete(imageFile);
    }

    @Override
    public ImageFile saveImage(ImageFile imageFile) {
        return imageFileRepository.save(imageFile);
    }
}
