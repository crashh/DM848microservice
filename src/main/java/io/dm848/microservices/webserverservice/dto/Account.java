package io.dm848.microservices.webserverservice.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.dm848.microservices.webserverservice.service.WebAccountsService;

/**
 * Account DTO - used to interact with the {@link WebAccountsService}.
 */
@JsonRootName("Account")
public class Account {

	protected Long id;
	protected String number;
	protected String owner;
	protected BigDecimal balance;

	/**
	 * Default constructor for JPA only.
	 */
	protected Account() {
		balance = BigDecimal.ZERO;
	}

	public long getId() {
		return id;
	}

	public String getNumber() {
		return number;
	}

	protected void setNumber(String accountNumber) {
		this.number = accountNumber;
	}

	public String getOwner() {
		return owner;
	}

	protected void setOwner(String owner) {
		this.owner = owner;
	}

	public BigDecimal getBalance() {
		return balance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}

	protected void setBalance(BigDecimal value) {
		balance = value;
		balance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}

	@Override
	public String toString() {
		return number + " [" + owner + "]: $" + balance;
	}

}
