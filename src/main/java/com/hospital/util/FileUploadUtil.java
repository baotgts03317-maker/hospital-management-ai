package com.hospital.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

public class FileUploadUtil {

    public static String saveFile(
            String uploadDir,
            MultipartFile file)
            throws IOException {

        String fileName =
                System.currentTimeMillis()
                + "_"
                + file.getOriginalFilename();

        Path uploadPath =
                Paths.get(uploadDir);

        if(!Files.exists(uploadPath)){

            Files.createDirectories(uploadPath);

        }

        Path filePath =
                uploadPath.resolve(fileName);

        Files.copy(
                file.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
        );

        return fileName;
    }
}