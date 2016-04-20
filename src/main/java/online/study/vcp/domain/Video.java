package online.study.vcp.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Describes video model, which presents on video portal
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
@Document
public class Video {

    @Id
    private String id;

    private String title;

    private String description;

    @DBRef(lazy = true)
    private User owner;

    private List<String> thumbnails;

    @Field("video-url")
    private String videoUrl;

    public Video() {
        super();
    }

    public Video(String videoUrl, List<String> thumbnails) {
        this("Unknown", "", null, thumbnails, videoUrl);
    }

    public Video(String title, String description, User owner, List<String> thumbnails, String videoUrl) {
        super();
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.thumbnails = thumbnails;
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

    public List<String> getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(List<String> thumbnails) {
        this.thumbnails = thumbnails;
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
                + ", thumbnails=" + thumbnails + ", videoUrl=" + videoUrl + "]";
    }

}
