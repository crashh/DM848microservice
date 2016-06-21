package dm848.controller;

import dm848.SearchCriteria;
import dm848.dto.User;
import dm848.service.WebUsersService;
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

import java.util.List;
import java.util.logging.Logger;

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
		binder.setAllowedFields("userId", "searchText");
	}

	@RequestMapping("/dm848.users")
	public String goHome() {
		return "index";
	}

	@RequestMapping("/dm848.users/{userName}")
	public String byUserName(Model model,
			@PathVariable("userName") String userName) {

		logger.info("web-service byUserName() invoked: " + userName);

		User user = usersService.findByUserName(userName);
		logger.info("web-service byUserName() found: " + user);
		model.addAttribute("user", user);
		return "dm848.users/user";
	}

    @RequestMapping("/dm848.users/name/{text}")
    public String byName(Model model, @PathVariable("text") String name) {
        logger.info("web-service byName() invoked: " + name);

        List<User> users = usersService.byNameContains(name);
        logger.info("web-service byName() found: " + users);
        model.addAttribute("search", name);
        if (users != null)
            model.addAttribute("users", users);
        return "dm848.users/dm848.users";
    }

	@RequestMapping(value = "/dm848.users/search", method = RequestMethod.GET)
	public String searchForm(Model model) {
		model.addAttribute("searchCriteria", new SearchCriteria());
		return "dm848.users/userSearch";
	}

	@RequestMapping(value = "/dm848.users/dosearch")
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
			return byName(model, searchText);
		}
	}

	@RequestMapping(value = "/dm848.users/all/")
	public String findAll(Model model) {
		logger.info("web-service findAll() invoked.");

		List<User> users = usersService.findAll();

		logger.info("web-service findAll() found: " + users);
        model.addAttribute("search", "all");
        if (users != null)
            model.addAttribute("users", users);
        return "dm848.users/all";

	}
}
