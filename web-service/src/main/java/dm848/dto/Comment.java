package dm848.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import dm848.service.WebUsersService;

/**
 * User DTO - used to interact with the {@link WebUsersService}.
 */
@JsonRootName("Comment")
public class Comment {

	protected Long id;
	protected Long videoId;
	protected String userId;
	protected String comment;

	/**
	 * Default constructor for JPA only.
	 */
	protected Comment() {
	}

	public long getId() {
		return id;
	}

	public String getComment() {
		return comment;
	}

	protected void setComment(String comment) {
		this.comment = comment;
	}

	public String getUserId() {
		return userId;
	}

	protected void setUserId(String id) {
		this.userId = id;
	}

	public Long getVideoId() {
		return videoId;
	}

	protected void setVideoId(Long id) {
		this.videoId = id;
	}

	@Override
	public String toString() {
		return comment;
	}

}
