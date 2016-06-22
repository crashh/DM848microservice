package dm848;

import dm848.images.ImageRepository;
import dm848.images.ImageConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import java.util.logging.Logger;

/**
 * Run as a micro-service, registering with the Discovery Server (Eureka).
 * <p>
 * Note that the configuration for this application is imported from
 * {@link ImageConfiguration}. This is a deliberate separation of concerns.
 */
@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(ImageConfiguration.class)
public class ImageServer {

    @Autowired
    protected ImageRepository imageRepository;

    protected Logger logger = Logger.getLogger(ImageServer.class.getName());

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "image-server");
        SpringApplication.run(ImageServer.class, args);
    }
}
