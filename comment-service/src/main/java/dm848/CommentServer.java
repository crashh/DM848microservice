package dm848;

import dm848.comments.CommentRepository;
import dm848.comments.CommentsConfiguration;
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
 * {@link CommentsConfiguration}. This is a deliberate separation of concerns.
 */
@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(CommentsConfiguration.class)
public class CommentServer {

    @Autowired
    protected CommentRepository commentRepository;

    protected Logger logger = Logger.getLogger(CommentServer.class.getName());

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "comment-server");

        if (args.length == 1) {
            System.getProperties().put( "server.port", args[0] );
        }

        SpringApplication.run(CommentServer.class, args);
    }
}
