package com.example.demo.controller;
import com.example.demo.exeptions.BadRequestException;
import com.example.demo.model.Image;
import com.example.demo.model.repository.ImageRepository;
import com.example.demo.model.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Files;


@RestController
public class ImageController extends AbstractController {

    private static final String IMAGES_DIR = "C:\\Users\\ngushlekov\\Desktop\\images\\Sport";

    @Autowired
    ImageRepository imageRepository;
    @Autowired
    NewsRepository newsRepository;


    @PutMapping(value = "/{id}/images")
    public Image upload(@RequestPart MultipartFile file, @PathVariable long id) throws IOException {
        File pFile = new File(IMAGES_DIR + File.separator + System.currentTimeMillis() + ".png");

        if (file.isEmpty()){
            throw new BadRequestException("Please, choose a file");
        }
        OutputStream out = new FileOutputStream(pFile);
            out.write(file.getBytes());
            Image newsImage = new Image();
            newsImage.setUrl(pFile.getAbsolutePath());
            newsImage.setNews(newsRepository.findById(id).get());
            imageRepository.save(newsImage);
            out.close();
            return newsImage;
    }

   @GetMapping(value = "/image/{id}")
    public byte[] download(@PathVariable long id) throws IOException {
        if (imageRepository.findById(id).isEmpty()){
            throw new BadRequestException("File not found");
        }
        Image newsImage = imageRepository.findById(id).get();
        String url = newsImage.getUrl();
        File pFile = new File(url);

        return Files.readAllBytes(pFile.toPath());
    }



}
