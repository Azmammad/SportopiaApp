package com.matrix.sportopia.mapper.mappingUtil;

import com.matrix.sportopia.exceptions.handle.FileIOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class UploadPathUtility {
    public static String uploadPath(MultipartFile file, String UPLOAD_DIR) {
       try {
            String originalFilename = file.getOriginalFilename();
            Path targetPath = Paths.get(UPLOAD_DIR, originalFilename);
            int counter = 1;
            while (Files.exists(targetPath)) {
                String baseName = FilenameUtils.getBaseName(originalFilename);
                String extension = FilenameUtils.getExtension(originalFilename);
                String newFilename = baseName + "_" + counter + "." + extension;
                targetPath = Paths.get(UPLOAD_DIR, newFilename);
                counter++;
            }
           Files.copy(file.getInputStream(), targetPath);
           return targetPath.toString();
        } catch (Exception e) {
            log.error("error due to " + e.getMessage());
            throw new FileIOException(e.getMessage());
        }
    }
}