package com.maliavin.vcp.form;

import org.springframework.web.multipart.MultipartFile;

public class ThumbnailForm {
    private MultipartFile file;

    public ThumbnailForm() {
        super();
    }

    public ThumbnailForm(MultipartFile file) {
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
