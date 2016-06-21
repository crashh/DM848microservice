package dm848.service;

import dm848.dto.Video;
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
public class WebVideoService {

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	protected String serviceUrl;

	protected Logger logger = Logger.getLogger(WebVideoService.class
			.getName());

	public WebVideoService(String serviceUrl) {
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

	public Video findById(String id) {
		logger.info("service-findById() invoked: for " + id);
		return restTemplate.getForObject(serviceUrl + "/videos/{id}",
				Video.class, id);
	}

	public List<Video> findAll() {
        logger.info("service-findAll() invoked.");

		Video[] Videos = restTemplate.getForObject(serviceUrl
				+ "/videos/all/", Video[].class);

		if (Videos == null)
			return null;
		else
			return Arrays.asList(Videos);
	}

	public List<Video> findSpotlight() {
		logger.info("service-findSpotlight() invoked.");

		Video[] videos = restTemplate.getForObject(serviceUrl
				+ "/videos/all/", Video[].class);

		Random rand = new Random();
		List<Video> spotlights = new ArrayList<>();

		if (videos == null || videos.length == 0) {
			return spotlights;
		}

		Video picked = null;
		for(int i=0; i<3; i++) {
			while (picked == null || spotlights.contains(picked)) {
				picked = videos[rand.nextInt(videos.length-1)];
			}
			spotlights.add(picked);
		}

		return spotlights;
	}
}
