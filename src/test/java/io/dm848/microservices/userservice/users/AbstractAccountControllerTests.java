package io.dm848.microservices.userservice.users;

import java.util.List;
import java.util.logging.Logger;

import io.dm848.microservices.exceptions.UserNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractAccountControllerTests {

	protected static final String ACCOUNT_1 = "123456789";
	protected static final String ACCOUNT_1_NAME = "Keri Lee";

	@Autowired
	UsersController accountController;

	@Test
	public void validAccountNumber() {
		Logger.getGlobal().info("Start validAccountNumber test");
		User user = accountController.byNumber(ACCOUNT_1);

		Assert.assertNotNull(user);
		Assert.assertEquals(ACCOUNT_1, user.getNumber());
		Assert.assertEquals(ACCOUNT_1_NAME, user.getOwner());
		Logger.getGlobal().info("End validAccount test");
	}
	
	@Test
	public void validAccountOwner() {
		Logger.getGlobal().info("Start validAccount test");
		List<User> users = accountController.byOwner(ACCOUNT_1_NAME);
		Logger.getGlobal().info("In validAccount test");

		Assert.assertNotNull(users);
		Assert.assertEquals(1, users.size());

		User user = users.get(0);
		Assert.assertEquals(ACCOUNT_1, user.getNumber());
		Assert.assertEquals(ACCOUNT_1_NAME, user.getOwner());
		Logger.getGlobal().info("End validAccount test");
	}

	@Test
	public void validAccountOwnerMatches1() {
		Logger.getGlobal().info("Start validAccount test");
		List<User> users = accountController.byOwner("Keri");
		Logger.getGlobal().info("In validAccount test");

		Assert.assertNotNull(users);
		Assert.assertEquals(1, users.size());

		User user = users.get(0);
		Assert.assertEquals(ACCOUNT_1, user.getNumber());
		Assert.assertEquals(ACCOUNT_1_NAME, user.getOwner());
		Logger.getGlobal().info("End validAccount test");
	}
	
	@Test
	public void validAccountOwnerMatches2() {
		Logger.getGlobal().info("Start validAccount test");
		List<User> users = accountController.byOwner("keri");
		Logger.getGlobal().info("In validAccount test");

		Assert.assertNotNull(users);
		Assert.assertEquals(1, users.size());

		User user = users.get(0);
		Assert.assertEquals(ACCOUNT_1, user.getNumber());
		Assert.assertEquals(ACCOUNT_1_NAME, user.getOwner());
		Logger.getGlobal().info("End validAccount test");
	}

	@Test
	public void invalidAccountNumber() {
		try {
			accountController.byNumber("10101010");
			Assert.fail("Expected an UserNotFoundException");
		} catch (UserNotFoundException e) {
			// Worked!
		}
	}

	@Test
	public void invalidAccountName() {
		try {
			accountController.byOwner("Fred Smith");
			Assert.fail("Expected an UserNotFoundException");
		} catch (UserNotFoundException e) {
			// Worked!
		}
	}
}
