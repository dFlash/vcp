package com.maliavin.vcp.form;

import org.springframework.web.multipart.MultipartFile;

/**
 * Stores required data for uploading video.
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public class UploadForm {
    private String title;
    private String description;
    private MultipartFile file;

    public UploadForm() {
        super();
    }

    public UploadForm(String title, String description, MultipartFile file) {
        super();
        this.title = title;
        this.description = description;
        this.file = file;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "UploadForm [title=" + title + ", description=" + description + ", file=" + file.toString() + "]";
    }
    
}
