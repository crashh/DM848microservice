package io.dm848.microservices.webserverservice.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.dm848.microservices.webserverservice.service.WebUsersService;

/**
 * User DTO - used to interact with the {@link WebUsersService}.
 */
@JsonRootName("User")
public class User {

	protected Long id;
	protected String userName;
	protected String name;

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

	@Override
	public String toString() {
		return userName + " [" + name + "]: $";
	}

}
