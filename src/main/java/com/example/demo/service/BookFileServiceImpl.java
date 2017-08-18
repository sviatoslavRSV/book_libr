package com.example.demo.service;


import com.example.demo.model.library.BookFile;
import com.example.demo.model.library.ImageFile;
import com.example.demo.repository.BookFileRepository;
import com.example.demo.repository.ImageFileRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookFileServiceImpl implements BookFileService {
    @Autowired
    private BookFileRepository bookFileRepository;
    @Autowired
    private ImageFileRepository imageFileRepository;

    @Override
    public ImageFile getImage(int id) {
        return imageFileRepository.findById(id);
    }

    @Override
    public ImageFile getImageFile(String imageName) {
        return imageFileRepository.findByImageName(imageName);
    }

    @Override
    public BookFile getBookFile(String fileName) {
        return bookFileRepository.findByFileName(fileName);
    }

    @Override
    public ImageFile saveImage(ImageFile imageFile) {
        return imageFileRepository.save(imageFile);
    }

    @Override
    public ImageFile updateImage(ImageFile imageFile) {
        String extension = FilenameUtils.getExtension(imageFile.getImageName());
        imageFile.setWebPath(imageFile.getWebPath() + imageFile.getId() + "." + extension);
        imageFile.setSystemPath(imageFile.getSystemPath() + imageFile.getId() + "." + extension);
        return imageFileRepository.save(imageFile);
    }
}
