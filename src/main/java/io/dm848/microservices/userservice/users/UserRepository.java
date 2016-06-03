package io.dm848.microservices.userservice.users;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

/**
 * Repository for User data implemented using Spring Data JPA.
 */
public interface UserRepository extends Repository<User, Long> {
	/**
	 * Find an controller with the specified controller userName.
	 *
	 * @param userName
	 * @return The controller if found, null otherwise.
	 */
	public User findByUserName(String userName);

	/**
	 * Find account whose name name contains the specified string
	 * 
	 * @param partialName
	 *            Any alphabetic string.
	 * @return The list of matching userservice - always non-null, but may be
	 *         empty.
	 */
	public List<User> findByNameContainingIgnoreCase(String partialName);

	/**
	 * Fetch the userName of users known to the system.
	 * 
	 * @return The userName of users.
	 */
	@Query("SELECT count(*) from User")
	public int countUsers();
}
