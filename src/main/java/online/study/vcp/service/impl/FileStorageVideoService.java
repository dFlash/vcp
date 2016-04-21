package online.study.vcp.service.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import online.study.vcp.domain.Video;
import online.study.vcp.exception.ApplicationException;
import online.study.vcp.service.ThumbnailService;
import online.study.vcp.service.VideoService;

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
		List<String> thumbnails = thumbnailService.createThumbnails(videoFilePath);
		LOGGER.info("new video successful uploaded: {}", videoFilePath.getFileName());
		return new Video("/media/video/"+uniqueVideoFileName, thumbnails);
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
