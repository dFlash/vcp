package com.maliavin.vcp.security;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.maliavin.vcp.domain.User;

/**
 * Util class for security operations.
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public class SecurityUtils {

    public static @Nullable User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof CurrentUser) {
            return ((CurrentUser) principal).getUser();
        } else {
            return null;
        }
    }

    public static void addPrincipalHeaders(HttpServletResponse resp) {
        User account = SecurityUtils.getCurrentUser();
        if (account != null) {
            resp.setHeader("PrincipalName", account.getName());
            resp.setHeader("PrincipalRole", account.getRole());
        }
    }
}
