package io.dm848.microservices.accountsservice.accounts;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

/**
 * Repository for Account data implemented using Spring Data JPA.
 */
public interface AccountRepository extends Repository<Account, Long> {
	/**
	 * Find an controller with the specified controller number.
	 *
	 * @param accountNumber
	 * @return The controller if found, null otherwise.
	 */
	public Account findByNumber(String accountNumber);

	/**
	 * Find account whose owner name contains the specified string
	 * 
	 * @param partialName
	 *            Any alphabetic string.
	 * @return The list of matching accountsservice - always non-null, but may be
	 *         empty.
	 */
	public List<Account> findByOwnerContainingIgnoreCase(String partialName);

	/**
	 * Fetch the number of accounts known to the system.
	 * 
	 * @return The number of accounts.
	 */
	@Query("SELECT count(*) from Account")
	public int countAccounts();
}
