package com.maliavin.vcp.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.maliavin.vcp.Platform;
import com.maliavin.vcp.component.UploadVideoTempStorage;
import com.maliavin.vcp.exception.CantProcessMediaContentException;
import com.maliavin.vcp.service.CommandExecutorService;
import com.maliavin.vcp.service.ThumbnailService;

@Service("ffmpegThumbnailService")
public class FfmpegThumbnailService implements ThumbnailService {

    private final static String WINDOWS_COMMAND = "ffmpeg.exe";

    private final static String LINUX_COMMAND = "ffmpeg";

    @Value("${thumbnail.seconds.range}")
    private String secondsRange;

    @Autowired
    private UploadVideoTempStorage uploadVideoTempStorage;

    private String tempImg;

    @Autowired
    private CommandExecutorService commandExecutorService;

    @Override
    public byte[] createThumbnail(Path videoFilePath) throws CantProcessMediaContentException {
        try {
            return createThumbnailInternal(videoFilePath);
        } catch (IOException e) {
            throw new CantProcessMediaContentException("Can't create thumbnail: " + e.getMessage(), e);
        }
    }

    private byte[] createThumbnailInternal(Path videoFilePath) throws IOException {
        String cmd = createCommand(videoFilePath);
        commandExecutorService.executeCommand(cmd);
        BufferedImage img = ImageIO.read(new File(tempImg));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", out);
        return out.toByteArray();
    }

    private String createCommand(Path videoFilePath) {
        String cmd = "";
        String videoPath = videoFilePath.toFile().getAbsolutePath();
        tempImg = getOutImgName();
        uploadVideoTempStorage.setTempThumbnail(tempImg);
        Random random = new Random();
        int frameSecond = random.nextInt(Integer.parseInt(secondsRange));
        if (Platform.isWindows()) {
            cmd += WINDOWS_COMMAND;
        } else {
            cmd += LINUX_COMMAND;
        }
        cmd += " -i " + videoPath + " -ss 00:00:" + frameSecond + ".000 " + "-vframes 1 " + tempImg;
        return cmd;
    }

    private String getOutImgName() {
        return UUID.randomUUID().toString() + ".jpg";
    }

}
