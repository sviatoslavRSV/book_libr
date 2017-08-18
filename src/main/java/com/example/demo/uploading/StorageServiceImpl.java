package com.example.demo.uploading;


import com.example.demo.model.library.ImageFile;
import org.apache.commons.io.FilenameUtils;
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
    //    private final static String url = "src/main/resources/upload";
    private final static String url = com.example.demo.model.Paths.SYSTEM_PATH_IMAGE.replaceFirst("/", "");
    private final Path path = Paths.get(url);
//    private MultipartFile multipartFile;

//    @Override
//    public MultipartFile getMultipartFile() {
//        return multipartFile;
//    }

    @Override
    public void store(MultipartFile file, ImageFile imageFile) {
//        multipartFile = file;
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file " + file.getOriginalFilename());
            }
            String extension = FilenameUtils.getExtension(imageFile.getImageName());
//            Files.copy(file.getInputStream(), path.resolve(myFile2.getFileName()),
            Files.copy(file.getInputStream(), path.resolve(String.valueOf(imageFile.getId() + "." +
                    extension)), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    /*@Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read stored files", e);
        }

    }*/

//    @Override
//    public Path loadFile(String filename) {
//        return path.resolve(filename);
//    }

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
