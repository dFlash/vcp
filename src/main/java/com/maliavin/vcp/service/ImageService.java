package com.maliavin.vcp.service;

import javax.annotation.Nonnull;

import com.maliavin.vcp.exception.CantProcessMediaContentException;

public interface ImageService {

    @Nonnull
    String saveImageData(@Nonnull byte[] imageBytes) throws CantProcessMediaContentException;
}
