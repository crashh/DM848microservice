package dm848.users;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

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
	 * @return The list of matching users - always non-null, but may be
	 *         empty.
	 */
	public List<User> findByNameContainingIgnoreCase(String partialName);

	/**
	 * Find all users in the database
	 *
	 * @return The list of all Users in the database.
     */
	public List<User> findAll();

	/**
	 * Fetch the number of users known to the system.
	 * 
	 * @return The number of users.
	 */
	@Query("SELECT count(*) from User")
	public int countUsers();
}
