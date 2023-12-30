package com.finalproject.travelagency.controller;


import org.springframework.http.HttpHeaders;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@RestController
@RequestMapping("/api/v1/auth/images")
@CrossOrigin(origins = "http://localhost:4200")
public class ImageController {

    private static final String IMAGE_DIRECTORY = "C:/Users/david/Desktop/Ostatni projekt/FInalProject/TravelAgency/images/";

    @GetMapping("/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
        Path imagePath = Paths.get(IMAGE_DIRECTORY + imageName);

        if (Files.exists(imagePath) && Files.isReadable(imagePath)) {
            byte[] imageBytes = Files.readAllBytes(imagePath);

            // Set appropriate headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG); // Adjust content type based on image type

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(imageBytes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}