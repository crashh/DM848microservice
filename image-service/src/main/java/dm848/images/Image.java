package dm848.images;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Persistent controller entity with JPA markup. Users are stored in an H2
 * relational database.
 */
@Entity
@Table(name = "T_IMAGE")
public class Image implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Long nextId = 0L;

	@Id
	protected Long id;

	protected String name;

	protected String link;

	protected String description;

	@Column(name = "cdate")
	protected String date;

	@Column(name = "user_name")
	protected String userName;


	/**
	 * This is a very simple, and non-scalable solution to generating unique
	 * ids. Not recommended for a real application. Consider using the
	 * <tt>@GeneratedValue</tt> annotation and a sequence to generate ids.
	 * 
	 * @return The next available id.
	 */
	protected static Long getNextId() {
		synchronized (nextId) {
			return nextId++;
		}
	}

	/**
	 * Default constructor for JPA only.
	 */
    public Image() {
    }

	public Image(String name, String link, String description, String username, String date) {
		id = getNextId();
		this.name = name;
		this.link = link;
		this.description = description;
		this.userName = username;
		this.date = date;
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

	public String getUserName() { return userName; }

	protected void setUserName(String username) {
		this.userName = username;
	}

	public String getDate() { return date; }

	@Override
	public String toString() {
		return name + " [" + link + "]";
	}

}
