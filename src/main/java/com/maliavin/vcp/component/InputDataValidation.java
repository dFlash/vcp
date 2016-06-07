package com.maliavin.vcp.component;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Component;

import com.maliavin.vcp.form.ChangePasswordForm;
import com.maliavin.vcp.form.ThumbnailForm;
import com.maliavin.vcp.form.UsernameForm;

/**
 * Aspect for input form validation (duplicate client validation)
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
@Aspect
@Component
public class InputDataValidation {

    @Before("execution(* com.maliavin.vcp.service.impl.AdminServiceImpl.uploadAvatar(..))")
    public void checkUploadAvatarForm(JoinPoint jp) {
        String email = (String) jp.getArgs()[0];
        if (email == null) {
            throw new ApplicationContextException("Data for avatar is incorrect");
        }
    }

    @Before("execution(* com.maliavin.vcp.service.impl.CommonServiceImpl.findUser(..))")
    public void checkUserExistingForUpdatePassword(JoinPoint jp) {
        String id = (String) jp.getArgs()[0];
        String hash = (String) jp.getArgs()[1];
        if (id == null || hash == null) {
            throw new ApplicationContextException("User identifier or hash is incorrect");
        }
    }

    @Before("execution(* com.maliavin.vcp.service.impl.CommonServiceImpl.sendMail(..))")
    public void checkUserExistingForEmailSending(JoinPoint jp) {
        UsernameForm usernameForm = (UsernameForm) jp.getArgs()[0];
        if (usernameForm == null || usernameForm.getUsername() == null) {
            throw new ApplicationContextException("User data for sending email is incorrect");
        }
    }

    @Before("execution(* com.maliavin.vcp.service.impl.CommonServiceImpl.changePassword(..))")
    public void checkUserNewPassword(JoinPoint jp) {
        ChangePasswordForm changePasswordForm = (ChangePasswordForm) jp.getArgs()[0];
        if (changePasswordForm == null || changePasswordForm.getNewPassword() == null
                || changePasswordForm.getRepeatPassword() == null || changePasswordForm.getUserId() == null) {
            throw new ApplicationContextException("User data for changing password is incorrect");
        }
    }

    @Before("execution(* com.maliavin.vcp.service.impl.UserServiceImpl.uploadThumbnail(..))")
    public void checkThumbnailForm(JoinPoint jp) {
        ThumbnailForm thumbnailForm = (ThumbnailForm) jp.getArgs()[0];
        if (thumbnailForm == null || thumbnailForm.getFile() == null) {
            throw new ApplicationContextException("Thumbnail data is incorrect");
        }
    }

}
