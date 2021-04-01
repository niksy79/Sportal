package com.example.demo.service;

import com.example.demo.exeptions.NotFoundException;
import com.example.demo.model.Comment;
import com.example.demo.model.Image;
import com.example.demo.model.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    public Image getImageById(long id){
        Optional<Image> Image = imageRepository.findById(id);
        if (Image.isEmpty()){
            throw new NotFoundException("Image not found");
        }
        return Image.get();
    }
}
