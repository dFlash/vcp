package com.maliavin.vcp.service;

import javax.annotation.Nonnull;

import com.maliavin.vcp.exception.CantProcessMediaContentException;

/**
 * Image service interface
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public interface ImageService {

    @Nonnull
    String saveImageData(@Nonnull byte[] imageBytes) throws CantProcessMediaContentException;
}
