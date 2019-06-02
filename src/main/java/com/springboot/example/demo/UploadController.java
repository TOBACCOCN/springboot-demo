package com.springboot.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class UploadController {

    private static Logger logger = LoggerFactory.getLogger(UploadController.class);

    @GetMapping("/upload")
    public String upload() {
        return "/upload";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "/uploadStatus";
    }

    @PostMapping("/upload")
    public String upload(MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        byte[] bytes = file.getBytes();
        String filePath = "D:/" + file.getOriginalFilename();
        File destFile = new File(filePath);
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        Path path = Paths.get(filePath);
        Files.write(path, bytes, StandardOpenOption.WRITE);
        logger.info(">>>>> UPLOAD_FILE_SIZE: {}", bytes.length);
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded '" + file.getOriginalFilename() + "'");
        return "redirect:uploadStatus";
    }

}
