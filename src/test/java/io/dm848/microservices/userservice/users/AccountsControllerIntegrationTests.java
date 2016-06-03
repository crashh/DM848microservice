package io.dm848.microservices.userservice.users;

import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.dm848.microservices.userservice.UserServer;

/**
 * Imitates the {@link UserServer}, but without using any of the discovery
 * client code. Allows the test to use the same configuration as the
 * <code>UserServer</code> would.
 * 
 * @author Paul Chapman
 *
 */
@SpringBootApplication
@Import(UsersConfiguration.class)
class AccountsMain {
	public static void main(String[] args) {
		// Tell server to look for userservice-server.properties or
		// userservice-server.yml
		System.setProperty("spring.config.name", "accounts-server");
		SpringApplication.run(AccountsMain.class, args);
	}
}

/**
 * Spring Integration/System test - by using @SpringApplicationConfiguration
 * instead of @ContextConfiguration, it picks up the same configuration that
 * Spring Boot would use.
 * 
 * @author Paul Chapman
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AccountsMain.class)
public class AccountsControllerIntegrationTests extends AbstractAccountControllerTests {

}
