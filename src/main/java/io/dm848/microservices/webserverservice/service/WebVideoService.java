package io.dm848.microservices.webserverservice.service;

import io.dm848.microservices.webserverservice.dto.User;
import io.dm848.microservices.webserverservice.dto.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
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

	public Video findById(String userName) {
		logger.info("findByUserName() invoked: for " + userName);
		return restTemplate.getForObject(serviceUrl + "/videos/{id}",
				Video.class, userName);
	}


	public List<Video> findAll() {
        logger.info("findAll() invoked.");

		Video[] Videos = restTemplate.getForObject(serviceUrl
				+ "/videos/all/", Video[].class);

		if (Videos == null)
			return null;
		else
			return Arrays.asList(Videos);
	}
}
