package io.dm848.microservices.userservice.users;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

/**
 * Repository for User data implemented using Spring Data JPA.
 */
public interface UserRepository extends Repository<User, Long> {
	/**
	 * Find an controller with the specified controller number.
	 *
	 * @param userNumber
	 * @return The controller if found, null otherwise.
	 */
	public User findByNumber(String userNumber);

	/**
	 * Find account whose owner name contains the specified string
	 * 
	 * @param partialName
	 *            Any alphabetic string.
	 * @return The list of matching userservice - always non-null, but may be
	 *         empty.
	 */
	public List<User> findByOwnerContainingIgnoreCase(String partialName);

	/**
	 * Fetch the number of users known to the system.
	 * 
	 * @return The number of users.
	 */
	@Query("SELECT count(*) from User")
	public int countUsers();
}
