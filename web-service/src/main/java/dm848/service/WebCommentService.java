package dm848.service;

import dm848.dto.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Hide the access to the microservice inside this local service.
 */
@Service
public class WebCommentService {

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	protected String serviceUrl;

	protected Logger logger = Logger.getLogger(WebCommentService.class
			.getName());

	public WebCommentService(String serviceUrl) {
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

	public List<Comment> findAll() {
		logger.info("service-findAll() invoked.");

		Comment[] comment = restTemplate.getForObject(serviceUrl
				+ "/comments/all/", Comment[].class);

		return Arrays.asList(comment);
	}

	public Comment findById(String id) {
		logger.info("service-findById() invoked: for " + id);
		return restTemplate.getForObject(serviceUrl + "/comments/{id}",
				Comment.class, id);
	}

	public List<Comment> byUserName(String username) {
        logger.info("service-byUserName() invoked.");

		Comment[] comment = restTemplate.getForObject(serviceUrl
				+ "/comments/user/{username}", Comment[].class, username);

		if (comment == null)
			return null;
		else
			return Arrays.asList(comment);
	}

	public List<Comment> byVideoId(Long id) {
		logger.info("service-byVideoId() invoked.");

		Comment[] comment = restTemplate.getForObject(serviceUrl
				+ "/comments/image/{id}", Comment[].class, id);

		if (comment == null)
			return null;
		else
			return Arrays.asList(comment);
	}

}
