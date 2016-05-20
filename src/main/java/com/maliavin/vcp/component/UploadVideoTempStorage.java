package com.maliavin.vcp.component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.maliavin.vcp.exception.CantProcessMediaContentException;
import com.maliavin.vcp.form.UploadForm;

/**
 * Aspect for temporary saving video file on file system.
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
@Aspect
@Component
public class UploadVideoTempStorage {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadVideoTempStorage.class);
    private Path tempUploadedVideoPathStorage;

    public Path getTempUploadedVideoPath() {
        return tempUploadedVideoPathStorage;
    }

    @Before("execution(* com.maliavin.vcp.service.impl.AsyncVideoProcessorService.processVideo(..))")
    public void advice(JoinPoint jp) throws Throwable {
        UploadForm form = (UploadForm) jp.getArgs()[0];
        try {
            tempUploadedVideoPathStorage = Files.createTempFile("upload", ".video");
            form.getFile().transferTo(tempUploadedVideoPathStorage.toFile());
        } catch (IOException | IllegalArgumentException e) {
            throw new CantProcessMediaContentException("Can't save video content to temp file: " + e.getMessage(), e);
        }
    }
    
    @After("execution(* com.maliavin.vcp.service.impl.SimpleVideoProcessorService.processVideo(..))")
    public void afterVideoProcessing() {
        try {
            Files.deleteIfExists(tempUploadedVideoPathStorage);
        } catch (IOException e) {
            LOGGER.warn("Can't remove temp file: " + tempUploadedVideoPathStorage, e);
        }
    }
}
