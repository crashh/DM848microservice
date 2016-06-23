package dm848.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import dm848.service.WebUsersService;

/**
 * User DTO - used to interact with the {@link WebUsersService}.
 */
@JsonRootName("User")
public class User {

	protected Long id;
	protected String userName;
	protected String name;
	protected String lastActive;

	/**
	 * Default constructor for JPA only.
	 */
	protected User() {
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

	public String getLastActive() { return this.lastActive; }

	@Override
	public String toString() {
		return userName + " [" + name + "]: $";
	}

}
