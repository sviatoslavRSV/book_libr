package com.example.demo.controller;

import com.example.demo.model.Paths;
import com.example.demo.model.library.ImageFile;
import com.example.demo.model.library.json_objects.JsonImageFile;
import com.example.demo.model.library.json_content.Files;
import com.example.demo.model.library.json_content.Upload;
import com.example.demo.model.library.json_objects.JsonBook;
import com.example.demo.model.library.json_objects.JsonMessage;
import com.example.demo.service.BookFileService;
import com.example.demo.uploading.StorageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FileUploadController {

    private final Logger logger = Logger.getLogger(FileUploadController.class);

    @Autowired
    private StorageService storageService;
    @Autowired
    private BookFileService bookFileService;
    @Autowired
    private MessageSource messageSource;

    @PostMapping("/admin/books/upload_image")
    @ResponseBody
    public ResponseEntity<?> handleFileUpload(@RequestParam("upload") MultipartFile file
            , HttpServletRequest request) {

        logger.warn("file: " + file.getOriginalFilename() + " : " + file.getContentType());

        if (file.getSize() > 10000) {
            logger.warn("file exceeds limit size");
            String message = messageSource.getMessage("image.size.err", null, request.getLocale());
            return new ResponseEntity<Object>(new JsonMessage("image", message)
                    , HttpStatus.OK);
        }
        JsonBook jsonBook = creatJsonImage(file);

//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.TEXT_HTML);
        logger.warn(jsonBook);
        return new ResponseEntity<>(jsonBook, HttpStatus.OK);
    }

    @GetMapping(value = "/admin/download_image/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getImageFile(@PathVariable String filename) {
        Resource fileImage = storageService.loadFileAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +
                fileImage.getFilename()).body(fileImage);
    }
    @GetMapping(value = "/admin/download_book/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getBookFile(@PathVariable String filename) {
        Resource fileImage = storageService.loadFileAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +
                fileImage.getFilename()).body(fileImage);
    }

    private JsonBook creatJsonImage(MultipartFile multipartFile) {

        ImageFile newImage = new ImageFile();
        newImage.setImageName(multipartFile.getOriginalFilename());
        newImage.setFileSize(String.valueOf(multipartFile.getSize()));
        newImage.setSystemPath(Paths.SYSTEM_PATH_IMAGE);
        newImage.setWebPath(Paths.WEB_PATH_IMAGE);

        newImage = bookFileService.saveImage(newImage);
        bookFileService.updateImage(newImage);

        JsonImageFile jsonImageFile = new JsonImageFile();
        jsonImageFile.setId(String.valueOf(newImage.getId()));
        jsonImageFile.setSystemPath(newImage.getSystemPath());
        jsonImageFile.setWebPath(newImage.getWebPath());
        jsonImageFile.setImageName(newImage.getImageName());
        jsonImageFile.setFileSize(newImage.getFileSize());

        JsonBook jsonBook = new JsonBook();
        Files files = new Files();
        Map<String, JsonImageFile> imageFileMap = new HashMap<>();
        imageFileMap.put(jsonImageFile.getId(), jsonImageFile);
        files.setFiles(imageFileMap);
        jsonBook.setFiles(files);
        Upload upload = new Upload();
        upload.setId(String.valueOf(newImage.getId()));
        jsonBook.setUploads(upload);

        storageService.store(multipartFile, newImage);
        logger.warn("image saved successfully name= " + newImage.getImageName() + " id= " + newImage.getId());
        return jsonBook;
    }
}

