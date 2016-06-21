package dm848.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import dm848.service.WebVideoService;

/**
 * User DTO - used to interact with the {@link WebVideoService}.
 */
@JsonRootName("Video")
public class Video {


	protected Long id;
	protected String name;
	protected String link;
	protected String embeddedLink; // Null by default
	protected String description;


	/**
	 * Default constructor for JPA only.
	 */
    public Video() {
    }

	public long getId() {
		return id;
	}

	public String getLink() {
		return link;
	}

	protected void setLink(String link) {
		this.link = link;
	}

	public String getEmbeddedLink() {
		return embeddedLink;
	}

	public void setEmbeddedLink(String link) {
		this.embeddedLink = link;
	}

	public String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	protected void setDescription(String desc) {
		this.description = desc;
	}

	@Override
	public String toString() {
		return name + " [" + link + "]";
	}

}
