package com.maliavin.vcp.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.Random;

import javax.imageio.ImageIO;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.FileChannelWrapper;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.stereotype.Service;

import com.maliavin.vcp.exception.CantProcessMediaContentException;
import com.maliavin.vcp.service.ThumbnailService;

@Service
public class JCodecThumbnailService implements ThumbnailService {

    @Override
    public byte[] createThumbnail(Path videoFilePath) {
        try {
            return createThumbnailInternal(videoFilePath);
        } catch (IOException | JCodecException e) {
            throw new CantProcessMediaContentException("Can't create thumbnail: " + e.getMessage(), e);
        }
    }

    private byte[] createThumbnailInternal(Path videoFilePath) throws IOException, JCodecException {
        Random random = new Random();
        Picture nativeFrame = getVideoFrameBySecondPrecise(videoFilePath, random.nextInt(15));
        if (nativeFrame == null) {
            throw new CantProcessMediaContentException(
                    "First video frame not found for video file: " + videoFilePath.getFileName());
        }
        BufferedImage img = AWTUtil.toBufferedImage(nativeFrame);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", out);
        return out.toByteArray();
    }

    private Picture getVideoFrameBySecondPrecise(Path videoFilePath, double secondPrecise)
            throws IOException, JCodecException {
        FrameGrab grab = new FrameGrab(new FileChannelWrapper(FileChannel.open(videoFilePath)));
        return grab.seekToSecondPrecise(secondPrecise).getNativeFrame();
    }
}
