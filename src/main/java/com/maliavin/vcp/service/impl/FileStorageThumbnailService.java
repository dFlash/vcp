package com.maliavin.vcp.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.FileChannelWrapper;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.maliavin.vcp.exception.ApplicationException;
import com.maliavin.vcp.service.ThumbnailService;

/**
 * Service which creates thumbnails for existing video
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
@Service
public class FileStorageThumbnailService implements ThumbnailService {

    private static final int FRAME_GRAB_RANGE = 20;

    private static final Logger LOGGER = LoggerFactory.getLogger(FileStorageThumbnailService.class);

    @Value("${media.dir}")
    private String mediaDir;

    @Override
    public String createThumbnail(Path videoFilePath) {
        try {
            return createThumbnailInternal(videoFilePath);
        } catch (IOException | JCodecException e) {
            throw new ApplicationException("create thumbnails failed: " + e.getMessage(), e);
        }
    }

    private String createThumbnailInternal(Path videoFilePath) throws IOException, JCodecException {
        String thumbnail = saveThumbnail(videoFilePath);
        LOGGER.info("Created thumbnail for video {}", videoFilePath.getFileName());
        return thumbnail;
    }

    private String saveThumbnail(Path videoFilePath) throws IOException, JCodecException {
        Picture nativeFrame = getVideoFrameBySecondPrecise(videoFilePath, new Random().nextInt(FRAME_GRAB_RANGE));
        if (nativeFrame == null) {
            throw new ApplicationException("Can't create thumbnail for file: " + videoFilePath.getFileName());
        }
        String uniquieThumbnailFileName = generateUniquieThumbnailFileName();
        savePictureToDisk(nativeFrame, uniquieThumbnailFileName);
        return "/media/thumbnails/" + uniquieThumbnailFileName;
    }

    private Picture getVideoFrameBySecondPrecise(Path videoFilePath, double secondPrecise)
            throws IOException, JCodecException {
        FrameGrab grab = new FrameGrab(new FileChannelWrapper(FileChannel.open(videoFilePath)));
        return grab.seekToSecondPrecise(secondPrecise).getNativeFrame();
    }

    private void savePictureToDisk(Picture nativeFrame, String uniquieThumbnailFileName) throws IOException {
        BufferedImage img = AWTUtil.toBufferedImage(nativeFrame);
        ImageIO.write(img, "jpg", new File(mediaDir + "/thumbnails", uniquieThumbnailFileName));
    }

    private String generateUniquieThumbnailFileName() {
        return UUID.randomUUID() + ".jpg";
    }
}
