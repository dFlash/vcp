package com.maliavin.vcp.test;

import org.aspectj.lang.JoinPoint;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContextException;

import com.maliavin.vcp.component.InputDataValidation;
import com.maliavin.vcp.form.AvatarForm;
import com.maliavin.vcp.form.ChangePasswordForm;
import com.maliavin.vcp.form.ThumbnailForm;
import com.maliavin.vcp.form.UsernameForm;

public class InputDataValidationTest {

    private InputDataValidation inputDataValidation = new InputDataValidation();

    @Test(expected = ApplicationContextException.class)
    public void checkUploadAvatarFormNullTest() {
        AvatarForm avatarForm = null;
        Object[] objects = new Object[1];
        objects[0] = avatarForm;
        JoinPoint jp = Mockito.mock(JoinPoint.class);
        Mockito.when(jp.getArgs()).thenReturn(objects);
        inputDataValidation.checkUploadAvatarForm(jp);
    }

    @Test(expected = ApplicationContextException.class)
    public void checkUploadAvatarFormNullEmailTest() {
        AvatarForm avatarForm = new AvatarForm(null);
        Object[] objects = new Object[1];
        objects[0] = avatarForm;
        JoinPoint jp = Mockito.mock(JoinPoint.class);
        Mockito.when(jp.getArgs()).thenReturn(objects);
        inputDataValidation.checkUploadAvatarForm(jp);
    }

    @Test(expected = ApplicationContextException.class)
    public void checkUserExistingForUpdatePasswordIdNullTest() {
        String id = null;
        String hash = "hash";
        Object[] objects = new Object[2];
        objects[0] = id;
        objects[1] = hash;
        JoinPoint jp = Mockito.mock(JoinPoint.class);
        Mockito.when(jp.getArgs()).thenReturn(objects);
        inputDataValidation.checkUserExistingForUpdatePassword(jp);
    }

    @Test(expected = ApplicationContextException.class)
    public void checkUserExistingForUpdatePasswordHashNullTest() {
        String id = "id";
        String hash = null;
        Object[] objects = new Object[2];
        objects[0] = id;
        objects[1] = hash;
        JoinPoint jp = Mockito.mock(JoinPoint.class);
        Mockito.when(jp.getArgs()).thenReturn(objects);
        inputDataValidation.checkUserExistingForUpdatePassword(jp);
    }

    @Test(expected = ApplicationContextException.class)
    public void checkUserExistingForEmailSendingFormNullTest() {
        UsernameForm usernameForm = null;
        Object[] objects = new Object[1];
        objects[0] = usernameForm;
        JoinPoint jp = Mockito.mock(JoinPoint.class);
        Mockito.when(jp.getArgs()).thenReturn(objects);
        inputDataValidation.checkUserExistingForEmailSending(jp);
    }

    @Test(expected = ApplicationContextException.class)
    public void checkUserExistingForEmailSendingUsernameNullTest() {
        UsernameForm usernameForm = new UsernameForm(null);
        Object[] objects = new Object[1];
        objects[0] = usernameForm;
        JoinPoint jp = Mockito.mock(JoinPoint.class);
        Mockito.when(jp.getArgs()).thenReturn(objects);
        inputDataValidation.checkUserExistingForEmailSending(jp);
    }

    @Test(expected = ApplicationContextException.class)
    public void checkUserNewPasswordFormNullTest() {
        ChangePasswordForm changePasswordForm = null;
        Object[] objects = new Object[1];
        objects[0] = changePasswordForm;
        JoinPoint jp = Mockito.mock(JoinPoint.class);
        Mockito.when(jp.getArgs()).thenReturn(objects);
        inputDataValidation.checkUserExistingForEmailSending(jp);
    }

    @Test(expected = ApplicationContextException.class)
    public void checkThumbnailFormFormNullTest() {
        ThumbnailForm thumbnailForm = null;
        Object[] objects = new Object[1];
        objects[0] = thumbnailForm;
        JoinPoint jp = Mockito.mock(JoinPoint.class);
        Mockito.when(jp.getArgs()).thenReturn(objects);
        inputDataValidation.checkUserExistingForEmailSending(jp);
    }

    @Test(expected = ApplicationContextException.class)
    public void checkThumbnailFormFileNullTest() {
        ThumbnailForm thumbnailForm = new ThumbnailForm(null);
        Object[] objects = new Object[1];
        objects[0] = thumbnailForm;
        JoinPoint jp = Mockito.mock(JoinPoint.class);
        Mockito.when(jp.getArgs()).thenReturn(objects);
        inputDataValidation.checkThumbnailForm(jp);
    }

}
