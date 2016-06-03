package io.dm848.microservices.userservice.users;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

public class AccountsControllerTests extends AbstractAccountControllerTests {

	protected static final User THE_USER = new User(ACCOUNT_1,
			ACCOUNT_1_NAME);

	protected static class TestUserRepository implements UserRepository {

		@Override
		public User findByNumber(String accountNumber) {
			if (accountNumber.equals(ACCOUNT_1))
				return THE_USER;
			else
				return null;
		}

		@Override
		public List<User> findByOwnerContainingIgnoreCase(String partialName) {
			List<User> users = new ArrayList<User>();

			if (ACCOUNT_1_NAME.toLowerCase().indexOf(partialName.toLowerCase()) != -1)
				users.add(THE_USER);

			return users;
		}

		@Override
		public int countUsers() {
			return 1;
		}
	}

	protected TestUserRepository testRepo = new TestUserRepository();

	@Before
	public void setup() {
		accountController = new UsersController(testRepo);
	}
}
