package com.maliavin.vcp.service;

import java.nio.file.Path;

/**
 * Thumbnail service interface
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public interface ThumbnailService {

    String createThumbnail(Path videoFilePath);
}
