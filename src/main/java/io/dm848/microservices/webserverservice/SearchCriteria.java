package io.dm848.microservices.webserverservice;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

public class SearchCriteria {
	private String userName;

	private String searchText;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public boolean isValid() {
		if (StringUtils.hasText(userName))
			return !(StringUtils.hasText(searchText));
		else
			return (StringUtils.hasText(searchText));
	}

	public boolean validate(Errors errors) {
		if (StringUtils.hasText(userName)) {
			if (userName.length() < 3)
				errors.rejectValue("userName", "badFormat",
						"User userName should be atleast 3 digits");

			if (StringUtils.hasText(searchText)) {
				errors.rejectValue("searchText", "nonEmpty",
						"Cannot specify controller userName and search text");
			}
		} else if (StringUtils.hasText(searchText)) {
			; // Nothing to do
		} else {
			errors.rejectValue("userName", "nonEmpty",
					"Must specify either an controller userName or search text");

		}

		return errors.hasErrors();
	}

	@Override
	public String toString() {
		return (StringUtils.hasText(userName) ? "userName: " + userName
				: "")
				+ (StringUtils.hasText(searchText) ? " text: " + searchText
						: "");
	}
}
