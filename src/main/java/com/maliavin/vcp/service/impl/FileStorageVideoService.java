package com.maliavin.vcp.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.maliavin.vcp.exception.CantProcessMediaContentException;
import com.maliavin.vcp.service.VideoService;

/**
 * Service which stores videos in file system.
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
@Service
public class FileStorageVideoService implements VideoService {

    @Value("${media.dir}")
    private String mediaDir;

    private String generateUniquieVideoFileName() {
        return UUID.randomUUID() + ".mp4";
    }

    @Override
    public String saveVideo(Path tempFilePath) {
        try {
            return saveVideoInternal(tempFilePath);
        } catch (IOException e) {
            throw new CantProcessMediaContentException("save video failed: " + e.getMessage(), e);
        }
    }

    private String saveVideoInternal(Path tempFilePath) throws IOException {
        String uniqueVideoFileName = generateUniquieVideoFileName();
        Path videoFilePath = Paths.get(mediaDir + "/video/" + uniqueVideoFileName);
        Files.copy(tempFilePath, videoFilePath);
        return "/media/video/" + uniqueVideoFileName;
    }
}
