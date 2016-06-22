package dm848.service;

import dm848.dto.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Hide the access to the microservice inside this local service.
 */
@Service
public class WebImageService {

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	protected String serviceUrl;

	protected Logger logger = Logger.getLogger(WebImageService.class
			.getName());

	public WebImageService(String serviceUrl) {
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

	public Image findById(String id) {
		logger.info("service-findById() invoked: for " + id);
		return restTemplate.getForObject(serviceUrl + "/images/{id}",
				Image.class, id);
	}

	public List<Image> findAll() {
        logger.info("service-findAll() invoked.");

		Image[] images = restTemplate.getForObject(serviceUrl
				+ "/images/all/", Image[].class);

		if (images == null)
			return null;
		else
			return Arrays.asList(images);
	}

	public List<Image> findSpotlight() {
		logger.info("service-findSpotlight() invoked.");

		Image[] images = restTemplate.getForObject(serviceUrl
				+ "/images/all/", Image[].class);

		Random rand = new Random();
		List<Image> spotlights = new ArrayList<>();

		if (images == null || images.length == 0) {
			return spotlights;
		}

		Image picked = null;
		for(int i=0; i<3; i++) {
			while (picked == null || spotlights.contains(picked)) {
				picked = images[rand.nextInt(images.length-1)];
			}
			spotlights.add(picked);
		}

		return spotlights;
	}

	public List<Image> findByUserName(String username) {
		logger.info("service-findByUserName() invoked.");

		Image[] images = restTemplate.getForObject(serviceUrl
				+ "/images/user/{username}", Image[].class, username);

		if (images == null)
			return null;
		else
			return Arrays.asList(images);
	}
}
