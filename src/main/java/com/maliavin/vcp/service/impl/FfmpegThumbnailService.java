package com.maliavin.vcp.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FfmpegThumbnailService.class);

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
            LOGGER.error("[ERROR] in createThumbnail= "+e.getMessage());
            throw new CantProcessMediaContentException("Can't create thumbnail: " + e.getMessage(), e);
        }
    }

    private byte[] createThumbnailInternal(Path videoFilePath) throws IOException {
        List<String> cmd = createCommand(videoFilePath);
        commandExecutorService.executeCommand(cmd);
        File f = new File(tempImg);
        BufferedImage img = ImageIO.read(f);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", out);
        return out.toByteArray();
    }

    private List<String> createCommand(Path videoFilePath) {
        List<String> cmd = new ArrayList<>();
        String ffmpegCmd = "";
        String videoPath = videoFilePath.toFile().getAbsolutePath();
        tempImg = getOutImgName();
        uploadVideoTempStorage.setTempThumbnail(tempImg);
        Random random = new Random();
        int frameSecond = random.nextInt(Integer.parseInt(secondsRange));
        if (Platform.isWindows()) {
            cmd.add("cmd.exe");
            cmd.add("/C");
            ffmpegCmd += WINDOWS_COMMAND;
        } else {
            cmd.add("/bin/bash");
            cmd.add("-c");
            ffmpegCmd += LINUX_COMMAND;
        }
        ffmpegCmd += " -i " + videoPath + " -ss 00:00:" + frameSecond + ".000 " + "-vframes 1 " + tempImg;
        cmd.add(ffmpegCmd);
        return cmd;
    }

    private String getOutImgName() {
        return UUID.randomUUID().toString() + ".jpg";
    }

}
