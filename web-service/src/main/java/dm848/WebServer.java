package dm848;

import dm848.controller.HomeController;
import dm848.controller.WebCommentController;
import dm848.controller.WebUsersController;
import dm848.controller.WebImageController;
import dm848.service.WebCommentService;
import dm848.service.WebUsersService;
import dm848.service.WebImageService;
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

	public static void main(String[] args) {
		// Tell server to look for web-server.properties or web-server.yml
		System.setProperty("spring.config.name", "web-server");
		SpringApplication.run(WebServer.class, args);
	}

	/**
	 * URL uses the logical name of controller-service - upper or lower case,
	 * doesn't matter.
	 */
	public static final String USER_SERVICE_URL = "USER-SERVICE";
	public static final String IMAGE_SERVICE_URL = "IMAGE-SERVICE";
	public static final String COMMENT_SERVICE_URL = "COMMENT-SERVICE";

	/**
	 * Create the services.
	 */
	@Bean
	public WebUsersService usersService() {
		return new WebUsersService(USER_SERVICE_URL);
	}
	@Bean
	public WebImageService imageService() {
		return new WebImageService(IMAGE_SERVICE_URL);
	}
	@Bean
	public WebCommentService commentService() {
		return new WebCommentService(COMMENT_SERVICE_URL);
	}

	/**
	 * Create the controllers, passing them the service links to use.
	 */

	@Bean
	public HomeController homeController() {
		return new HomeController();
	}
	@Bean
	public WebUsersController usersController() {
		return new WebUsersController(usersService());
	}
	@Bean
	public WebImageController imageController() {
		return new WebImageController(imageService());
	}
	@Bean
	public WebCommentController commentController() {
		return new WebCommentController(commentService());
	}
}
