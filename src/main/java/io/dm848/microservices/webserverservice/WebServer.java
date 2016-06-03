package io.dm848.microservices.webserverservice;

import io.dm848.microservices.webserverservice.controller.HomeController;
import io.dm848.microservices.webserverservice.controller.WebUsersController;
import io.dm848.microservices.webserverservice.service.WebUsersService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * User web-server. Works as a microservice client, fetching data from the
 * User-Service. Uses the Discovery Server (Eureka) to find the microservice.
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(useDefaultFilters = false) // Disable component scanner
public class WebServer {

	/**
	 * URL uses the logical name of controller-service - upper or lower case,
	 * doesn't matter.
	 */
	public static final String USER_SERVICE_URL = "http://USER-SERVICE";

	public static void main(String[] args) {
		// Tell server to look for web-server.properties or web-server.yml
		System.setProperty("spring.config.name", "web-server");
		SpringApplication.run(WebServer.class, args);
	}

	/**
	 * The AccountService encapsulates the interaction with the micro-service.
	 * 
	 * @return A new service instance.
	 */
	@Bean
	public WebUsersService usersService() {
		return new WebUsersService(USER_SERVICE_URL);
	}

	/**
	 * Create the controller, passing it the {@link WebUsersService} to use.
	 * 
	 * @return
	 */
	@Bean
	public WebUsersController usersController() {
		return new WebUsersController(usersService());
	}

	@Bean
	public HomeController homeController() {
		return new HomeController();
	}
}
