package com.maliavin.vcp.service;

import java.nio.file.Path;

import javax.annotation.Nonnull;

import com.maliavin.vcp.exception.CantProcessMediaContentException;

/**
 * Video service interface
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public interface VideoService {

    @Nonnull
    String saveVideo(@Nonnull Path tempFilePath) throws CantProcessMediaContentException;
}
