package com.maliavin.vcp.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.maliavin.vcp.exception.CantProcessMediaContentException;
import com.maliavin.vcp.service.ImageService;

/**
 * Service which stores images (thumbnails) in file system.
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
@Service
public class FileStorageImageService implements ImageService {

    @Value("${thumbnails.dir}")
    private String thumbnailsMediaDir;

    @Override
    public String saveImageData(byte[] imageBytes) {
        try {
            return saveImageDataInternal(imageBytes);
        } catch (IOException e) {
            throw new CantProcessMediaContentException("Can't save image data: " + e.getMessage(), e);
        }
    }

    private String saveImageDataInternal(byte[] imageBytes) throws IOException {
        String uniquieThumbnailFileName = generateUniquieThumbnailFileName();
        Path path = Paths.get(thumbnailsMediaDir + "/" + uniquieThumbnailFileName);
        Files.write(path, imageBytes);
        return "/media/thumbnails/" + uniquieThumbnailFileName;
    }

    private String generateUniquieThumbnailFileName() {
        return UUID.randomUUID() + ".jpg";
    }
}
