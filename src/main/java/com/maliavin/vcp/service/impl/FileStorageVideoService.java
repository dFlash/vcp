package com.maliavin.vcp.service.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.maliavin.vcp.domain.Video;
import com.maliavin.vcp.exception.ApplicationException;
import com.maliavin.vcp.service.ThumbnailService;
import com.maliavin.vcp.service.VideoService;

/**
 * Service which processes video
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
@Service
public class FileStorageVideoService implements VideoService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileStorageVideoService.class);

	@Autowired
	private ThumbnailService thumbnailService;

	@Value("${media.dir}")
	private String mediaDir;

	@Override
	public Video processVideo(MultipartFile videoFile) {
		try {
			return processVideoInternal(videoFile);
		} catch (IOException e) {
			throw new ApplicationException("save video failed: " + e.getMessage(), e);
		}
	}
	
	private Video processVideoInternal(MultipartFile multipartVideoFile) throws IOException {
		String uniqueVideoFileName = generateUniquieVideoFileName();
		Path videoFilePath = saveMultipartFile(multipartVideoFile, uniqueVideoFileName);
		String thumbnail = thumbnailService.createThumbnail(videoFilePath);
		LOGGER.info("new video successful uploaded: {}", videoFilePath.getFileName());
		return new Video("/media/video/"+uniqueVideoFileName, thumbnail);
	}
	
	private Path saveMultipartFile(MultipartFile multipartVideoFile, String uniqueVideoFileName) throws IOException {
		Path videoFilePath = Paths.get(mediaDir+"/video/"+uniqueVideoFileName);
		multipartVideoFile.transferTo(videoFilePath.toFile());
		return videoFilePath;
	}

	private String generateUniquieVideoFileName() {
		return UUID.randomUUID()+".mp4";
	}
}
