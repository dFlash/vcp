package com.maliavin.vcp.form;

/**
 * Stores required data for uploading avatar.
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public class AvatarForm {
    private String email;

    public AvatarForm() {
        super();
    }

    public AvatarForm(String email) {
        super();
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
