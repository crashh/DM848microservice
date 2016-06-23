package dm848.users;

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
@Table(name = "T_USER")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Long nextId = 0L;

	@Id
	protected Long id;

    @Column(name = "user_name")
	protected String userName;

	protected String name;

	@Column(name = "last_active")
	protected String lastActive;


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
    public User() {
    }

	public User(String userName, String name, String active) {
		id = getNextId();
		this.userName = userName;
		this.name = name;
		this.lastActive = active;
	}

	public long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	protected void setUserName(String name) {
		this.userName = name;
	}

	public String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	public String getLastActive() {
		return lastActive;
	}

	protected void setLastActive(String lastActive) {
		this.lastActive = lastActive;
	}

	@Override
	public String toString() {
		return userName + " [" + name + "]: $";
	}

}
