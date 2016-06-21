package dm848;

import dm848.videos.VideoRepository;
import dm848.videos.VideosConfiguration;
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
 * {@link VideosConfiguration}. This is a deliberate separation of concerns.
 */
@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(VideosConfiguration.class)
public class VideoServer {

    @Autowired
    protected VideoRepository videoRepository;

    protected Logger logger = Logger.getLogger(VideoServer.class.getName());

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "video-server");
        SpringApplication.run(VideoServer.class, args);
    }
}
