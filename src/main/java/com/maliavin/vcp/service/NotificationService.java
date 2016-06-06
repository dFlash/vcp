package com.maliavin.vcp.service;

import javax.annotation.Nonnull;

import com.maliavin.vcp.domain.User;

/**
 * Notification service interface
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public interface NotificationService {

    void sendRestoreAccessLink(@Nonnull User profile, @Nonnull String restoreLink);
}
