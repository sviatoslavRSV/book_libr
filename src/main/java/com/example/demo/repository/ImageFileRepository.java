package com.example.demo.repository;


import com.example.demo.model.library.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {
    ImageFile findByImageName(String imageName);

    ImageFile findById(int id);

//    ImageFile findFirstByOrderByIdDesc();
}
