package online.study.vcp.service;

import java.nio.file.Path;
import java.util.List;

/**
 * Thumbnail service interface
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public interface ThumbnailService {

    List<String> createThumbnails(Path videoFilePath);
}
