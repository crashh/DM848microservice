package io.dm848.microservices.userservice.users;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import io.dm848.microservices.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A RESTFul controller for accessing controller information.
 */
@RestController
public class UsersController {

	protected Logger logger = Logger.getLogger(UsersController.class
			.getName());
	protected UserRepository userRepository;

	/**
	 * Create an instance plugging in the respository of Accounts.
	 * 
	 * @param userRepository
	 *            An controller repository implementation.
	 */
	@Autowired
	public UsersController(UserRepository userRepository) {
		this.userRepository = userRepository;

		logger.info("UserRepository says system has "
				+ userRepository.countUsers() + " users");
	}

	/**
	 * Fetch an user with the specified userName.
	 * 
	 * @param userName
	 *            A numeric, 9 digit controller userName.
	 * @return The controller if found.
	 * @throws UserNotFoundException
	 *             If the userName is not recognised.
	 */
	@RequestMapping("/users/{userName}")
	public User byUserName(@PathVariable("userName") String userName) {

		logger.info("user-service byUserName() invoked: " + userName);
		User user = userRepository.findByUserName(userName);
		logger.info("user-service byUserName() found: " + user);

		if (user == null)
			throw new UserNotFoundException(userName);
		else {
			return user;
		}
	}

	/**
	 * Fetch userservice with the specified name. A partial case-insensitive match
	 * is supported. So <code>http://.../users/owner/a</code> will find any
	 * userservice with upper or lower case 'a' in their name.
	 * 
	 * @param partialName
	 * @return A non-null, non-empty set of users.
	 * @throws UserNotFoundException
	 *             If there are no matches at all.
	 */
	@RequestMapping("/users/name/{name}")
	public List<User> byName(@PathVariable("name") String partialName) {

		logger.info("user-service byName() invoked: "
				+ userRepository.getClass().getName() + " for "
				+ partialName);

		List<User> users = userRepository
				.findByNameContainingIgnoreCase(partialName);

		logger.info("user-service byName() found: " + users);

		if (users == null || users.size() == 0)
			throw new UserNotFoundException(partialName);
		else {
			return users;
		}
	}

    /**
     * Fetch all Users
     *
     * @return a list containing all users.
     */
	@RequestMapping("/users/all/")
	public List<User> findAll() {

		logger.info("user-service findAll() invoked: "
				+ userRepository.getClass().getName());

		List<User> users = userRepository.findAll();

		logger.info("user-service findAll() found: " + users);

		if (users == null || users.size() == 0)
			return new ArrayList<>();
		else {
			return users;
		}
	}
}
