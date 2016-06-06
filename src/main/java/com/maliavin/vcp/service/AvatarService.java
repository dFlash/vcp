package com.maliavin.vcp.service;

import javax.annotation.Nonnull;

public interface AvatarService {
    
    @Nonnull String generateAvatarUrl(@Nonnull String email);

}
