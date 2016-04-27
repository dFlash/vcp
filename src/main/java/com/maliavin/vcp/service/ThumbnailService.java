package com.maliavin.vcp.service;

import java.nio.file.Path;

import javax.annotation.Nonnull;

import com.maliavin.vcp.exception.CantProcessMediaContentException;

/**
 * Thumbnail service interface
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public interface ThumbnailService {

    @Nonnull
    byte[] createThumbnail(@Nonnull Path videoFilePath) throws CantProcessMediaContentException;
}
