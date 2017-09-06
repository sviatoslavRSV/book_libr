package com.example.demo.controller;

import com.example.demo.model.library.ImageFile;
import com.example.demo.model.library.json_content.Files;
import com.example.demo.model.library.json_content.Upload;
import com.example.demo.model.library.json_objects.JsonBook;
import com.example.demo.model.library.json_objects.JsonImageFile;
import com.example.demo.model.library.json_objects.JsonMessage;
import com.example.demo.service.BookFileService;
import com.example.demo.service.uploading.StorageService;
import com.example.demo.utils.ExtensionsAndPaths;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
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
    public ResponseEntity<?> handleImageUpload(@RequestParam("upload") MultipartFile file
            , HttpServletRequest request) {
        logger.debug("uplaod imagefile: " + file.getOriginalFilename() + " : " + file.getContentType());
        return checkImage(file, request);
    }

    @PostMapping("/admin/books/upload_book")
    @ResponseBody
    public ResponseEntity<?> handleFileUpload(@RequestParam("upload") MultipartFile file
            , HttpServletRequest request) {
        logger.debug("upload bookfile: " + file.getOriginalFilename() + " : " + file.getContentType());
        return checkBookFile(file, request);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(value = "/user/download/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getImageFile(@PathVariable String filename) {
        Resource fileImage = storageService.loadFileAsResource(filename);
        logger.debug("file: " + filename + " downloaded with success");
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +
                fileImage.getFilename()).body(fileImage);
    }

    private ResponseEntity<?> checkImage(MultipartFile file, HttpServletRequest request) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file.getInputStream());
            logger.debug("height: " + bufferedImage.getHeight() + " weight: " + bufferedImage.getWidth());
        } catch (MalformedURLException e) {
            logger.warn(e.getMessage() + "exception");
        } catch (Exception e) {
            logger.warn(e.getMessage() + "exception");
        }

        if (!ExtensionsAndPaths.getExtensionsImageSet().contains(extension)) {
            String message = messageSource.getMessage("image.extens.err", null, request.getLocale());
            logger.debug("unregistered extension");
            return new ResponseEntity<Object>(new JsonMessage("image", message)
                    , HttpStatus.OK);
        }
        if (bufferedImage.getHeight() > 800 || bufferedImage.getWidth() > 600) {
            String message = messageSource.getMessage("image.size.err", null, request.getLocale());
            logger.debug("wrong resolution");
            return new ResponseEntity<Object>(new JsonMessage("image", message)
                    , HttpStatus.OK);
        }
        JsonBook jsonBook = creatJsonImage(file);
        return new ResponseEntity<>(jsonBook, HttpStatus.OK);
    }

    private ResponseEntity<?> checkBookFile(MultipartFile file, HttpServletRequest request) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!ExtensionsAndPaths.getExtensionsBookSet().contains(extension)) {
            String message = messageSource.getMessage("book.extens.err", null, request.getLocale());
            logger.debug("unregistered extension");
            return new ResponseEntity<Object>(new JsonMessage("book", message)
                    , HttpStatus.OK);
        }
        if (file.getSize() > 20000000) {
            String message = messageSource.getMessage("book.size.err", null, request.getLocale());
            logger.debug("wrong size");
            return new ResponseEntity<Object>(new JsonMessage("book", message)
                    , HttpStatus.OK);
        }
        JsonBook jsonBook = creatJsonImage(file);
        return new ResponseEntity<>(jsonBook, HttpStatus.OK);
    }

    private JsonBook creatJsonImage(MultipartFile multipartFile) {
/*set object image to write in database*/
        ImageFile newImage = new ImageFile();
        newImage.setImageName(multipartFile.getOriginalFilename());
        newImage.setFileSize(String.valueOf(multipartFile.getSize()));
        newImage.setSystemPath(ExtensionsAndPaths.SYSTEM_PATH + newImage.getImageName());
        newImage.setWebPath(ExtensionsAndPaths.WEB_PATH + newImage.getImageName());
/*write object in database and retreive id*/
        newImage = bookFileService.saveImage(newImage);
        logger.debug("image saved with success: " + newImage.getImageName());
/*create new json with field "String id" instead of "int id"*/
        JsonImageFile jsonImageFile = new JsonImageFile(newImage);
/*creat json object with structure for response*/
        JsonBook jsonBook = new JsonBook();
        Files files = new Files();
        Map<String, JsonImageFile> imageFileMap = new HashMap<>();
        imageFileMap.put(jsonImageFile.getId(), jsonImageFile);
        files.setFiles(imageFileMap);
        jsonBook.setFiles(files);
        Upload upload = new Upload();
        upload.setId(String.valueOf(newImage.getId()));
        jsonBook.setUploads(upload);
/*save file in system path*/
        storageService.store(multipartFile, newImage);
        logger.debug("image saved successfully name= " + newImage.getImageName() + " id= " + newImage.getId());
        return jsonBook;
    }
}

