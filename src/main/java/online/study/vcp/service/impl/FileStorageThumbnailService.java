package online.study.vcp.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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

import online.study.vcp.exception.ApplicationException;
import online.study.vcp.service.ThumbnailService;

@Service
public class FileStorageThumbnailService implements ThumbnailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileStorageThumbnailService.class);

    @Value("${media.dir}")
    private String mediaDir;

    @Override
    public List<String> createThumbnails(Path videoFilePath) {
        try {
            List<String> thumbnails = new ArrayList<String>();
            FrameGrab grab = new FrameGrab(new FileChannelWrapper(FileChannel.open(videoFilePath)));
            for (int i = 0; i < 1; i++) {
                Picture nativeFrame = grab.seekToSecondPrecise(new Random().nextInt(25)).getNativeFrame();
                if (nativeFrame != null) {
                    BufferedImage img = AWTUtil.toBufferedImage(nativeFrame);
                    String uid = UUID.randomUUID() + ".jpg";
                    ImageIO.write(img, "jpg", new File(mediaDir + "/thumbnails", uid));
                    thumbnails.add("/media/thumbnails/" + uid);
                }
            }
            LOGGER.info("Created {} thumbnails for video {}", thumbnails.size(), videoFilePath.getFileName());
            return thumbnails;
        } catch (IOException | JCodecException e) {
            throw new ApplicationException("create thumbnails failed: " + e.getMessage(), e);
        }
    }
}
