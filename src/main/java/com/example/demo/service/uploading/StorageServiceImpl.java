package com.example.demo.service.uploading;


import com.example.demo.model.library.ImageFile;
import com.example.demo.utils.ExtensionsAndPaths;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class StorageServiceImpl implements StorageService {
    private final static String url = ExtensionsAndPaths.SYSTEM_PATH.replaceFirst("/", "");
    private final Path path = Paths.get(url);

    @Override
    public void store(MultipartFile file, ImageFile imageFile) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file " + file.getOriginalFilename());
            }
//            String extension = FilenameUtils.getExtension(imageFile.getImageName());
//            Files.copy(file.getInputStream(), path.resolve(String.valueOf(imageFile.getId() + "." +
//                    extension)), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(file.getInputStream(), path.resolve(imageFile.getImageName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Resource loadFileAsResource(String filename) {
        try {
            Path file = path.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }
}
