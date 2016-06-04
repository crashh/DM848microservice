package io.dm848.microservices.webserverservice.controller;

import java.util.List;
import java.util.logging.Logger;

import io.dm848.microservices.webserverservice.SearchCriteria;
import io.dm848.microservices.webserverservice.dto.User;
import io.dm848.microservices.webserverservice.service.WebUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Client controller, fetches User info from the microservice via
 * {@link WebUsersService}.
 */
@Controller
public class WebUsersController {

	@Autowired
	protected WebUsersService usersService;

	protected Logger logger = Logger.getLogger(WebUsersController.class
			.getName());

	public WebUsersController(WebUsersService usersService) {
		this.usersService = usersService;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields("userNumber", "searchText");
	}

	@RequestMapping("/users")
	public String goHome() {
		return "index";
	}

	@RequestMapping("/users/{userName}")
	public String byUserName(Model model,
			@PathVariable("userName") String userName) {

		logger.info("web-service byUserName() invoked: " + userName);

		User user = usersService.findByUserName(userName);
		logger.info("web-service byUserName() found: " + user);
		model.addAttribute("user", user);
		return "users/user";
	}

    @RequestMapping("/users/name/{text}")
    public String ownerSearch(Model model, @PathVariable("text") String name) {
        logger.info("web-service byOwner() invoked: " + name);

        List<User> users = usersService.byNameContains(name);
        logger.info("web-service byOwner() found: " + users);
        model.addAttribute("search", name);
        if (users != null)
            model.addAttribute("users", users);
        return "users";
    }

	@RequestMapping(value = "/users/search", method = RequestMethod.GET)
	public String searchForm(Model model) {
		model.addAttribute("searchCriteria", new SearchCriteria());
		return "users/userSearch";
	}

	@RequestMapping(value = "/users/dosearch")
	public String doSearch(Model model, SearchCriteria criteria,
			BindingResult result) {
		logger.info("web-service search() invoked: " + criteria);

		criteria.validate(result);

		if (result.hasErrors())
			return "userSearch";

		String userName = criteria.getUserName();
		if (StringUtils.hasText(userName)) {
			return byUserName(model, userName);
		} else {
			String searchText = criteria.getSearchText();
			return ownerSearch(model, searchText);
		}
	}

	@RequestMapping(value = "/users/all/")
	public String findAll(Model model) {
		logger.info("web-service findAll() invoked.");

		List<User> users = usersService.findAll();

		logger.info("web-service findAll() found: " + users);
        model.addAttribute("search", "all");
        if (users != null)
            model.addAttribute("users", users);
        return "users/all";

	}
}
