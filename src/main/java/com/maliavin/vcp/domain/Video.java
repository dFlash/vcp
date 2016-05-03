package com.maliavin.vcp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Describes video model, which presents on video portal
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
@org.springframework.data.mongodb.core.mapping.Document
@org.springframework.data.elasticsearch.annotations.Document(indexName = "video")
public class Video {

    @Id
    private String id;

    private String title;

    private String description;

    @DBRef(lazy = true)
    private User owner;

    private String thumbnail;

    @Field("video-url")
    private String videoUrl;

    public Video() {
        super();
    }

    public Video(String videoUrl, String thumbnail) {
        this("Unknown", "", null, thumbnail, videoUrl);
    }

    public Video(String title, String description, User owner, String thumbnail, String videoUrl) {
        super();
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.thumbnail = thumbnail;
        this.videoUrl = videoUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public String toString() {
        return "Video [id=" + id + ", title=" + title + ", description=" + description + ", owner=" + owner
                + ", thumbnail=" + thumbnail + ", videoUrl=" + videoUrl + "]";
    }

}
