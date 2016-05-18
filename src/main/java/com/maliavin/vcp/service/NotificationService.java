package com.maliavin.vcp.service;

import com.maliavin.vcp.domain.User;

/**
 * Notification service interface
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public interface NotificationService {

    void sendRestoreAccessLink(User profile, String restoreLink);
}
