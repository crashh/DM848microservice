package dm848;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Starts up the Eureka registration server.
 */
@SpringBootApplication
@EnableEurekaServer
public class RegistrationServer {

    public static void main(String[] args) {
        // Tell server to look for registration-server.properties or registration-server.yml
        System.setProperty("spring.config.name", "registration-server");

        if (args.length == 1) {
            System.err.println("WARNING: Changing port on registration server will require " +
                    "config changes to the remaining services.");
            System.getProperties().put( "server.port", args[0] );
        }

        SpringApplication.run(RegistrationServer.class, args);
    }
}