package com.example.kristp.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileUpload {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String saveFile(MultipartFile file) throws IOException {
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
            UUID uuid = UUID.randomUUID();
            String newFileName = uuid + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir, newFileName);
            Files.write(path, file.getBytes());
            return newFileName;
    }
}
