package com.maliavin.vcp.form;

import org.springframework.web.multipart.MultipartFile;

/**
 * Stores required data for uploading avatar.
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public class AvatarForm {
    private MultipartFile file;

    public AvatarForm() {
        super();
    }

    public AvatarForm(MultipartFile file) {
        super();
        this.file = file;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

}
