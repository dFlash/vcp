package java.study.vcp.domain;

/**
 * Describes video model, which presents on video portal
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public class Video {

	private String title;

	private String description;

	private User user;

	private byte[] thumbnail;

	private byte[] content;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public byte[] getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(byte[] thumbnail) {
		this.thumbnail = thumbnail;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Video [title=" + title + ", description=" + description + ", user=" + user + "]";
	}

}
