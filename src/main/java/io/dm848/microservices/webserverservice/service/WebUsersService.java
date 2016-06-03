package io.dm848.microservices.webserverservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import io.dm848.microservices.exceptions.UserNotFoundException;
import io.dm848.microservices.webserverservice.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Hide the access to the microservice inside this local service.
 */
@Service
public class WebUsersService {

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	protected String serviceUrl;

	protected Logger logger = Logger.getLogger(WebUsersService.class
			.getName());

	public WebUsersService(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl
				: "http://" + serviceUrl;
	}

	/**
	 * The RestTemplate works because it uses a custom request-factory that uses
	 * Ribbon to look-up the service to use. This method simply exists to show
	 * this.
	 */
	@PostConstruct
	public void onLoaded() {
		// Can't do this in the constructor because the RestTemplate injection
		// happens afterwards.
		logger.warning("The RestTemplate request factory is "
				+ restTemplate.getRequestFactory().getClass());
	}

	public User findByUserName(String userName) {
		logger.info("findByUserName() invoked: for " + userName);
		return restTemplate.getForObject(serviceUrl + "/users/{userName}",
				User.class, userName);
	}

	public List<User> byNameContains(String name) {
		logger.info("byNameContains() invoked:  for " + name);
		User[] users = null;

		try {
			users = restTemplate.getForObject(serviceUrl
					+ "/users/name/{name}", User[].class, name);
		} catch (HttpClientErrorException e) {
            // 404 - Nothing found
		}

		if (users == null || users.length == 0)
			return null;
		else
			return Arrays.asList(users);
	}

	public User getByUserName(String userName) {
        logger.info("getByUserName() invoked:  for " + userName);

		User user = restTemplate.getForObject(serviceUrl
				+ "/users/{userName}", User.class, userName);

		if (user == null)
			throw new UserNotFoundException(userName);
		else
			return user;
	}
}
