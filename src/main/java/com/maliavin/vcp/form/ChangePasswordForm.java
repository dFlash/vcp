package com.maliavin.vcp.form;

/**
 * Stores required data for change password request.
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public class ChangePasswordForm {

    private String newPassword;

    private String repeatPassword;

    public ChangePasswordForm() {
        super();
    }

    public ChangePasswordForm(String newPassword, String repeatPassword, String userId) {
        super();
        this.newPassword = newPassword;
        this.repeatPassword = repeatPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

}
