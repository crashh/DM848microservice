package dm848;

import dm848.users.UserRepository;
import dm848.users.UsersConfiguration;
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
 * {@link UsersConfiguration}. This is a deliberate separation of concerns.
 */
@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(UsersConfiguration.class)
public class UserServer {

	@Autowired
	protected UserRepository userRepository;

	protected Logger logger = Logger.getLogger(UserServer.class.getName());

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "user-server");

		if (args.length == 1) {
			System.getProperties().put( "server.port", args[0] );
		}

		SpringApplication.run(UserServer.class, args);
	}
}
