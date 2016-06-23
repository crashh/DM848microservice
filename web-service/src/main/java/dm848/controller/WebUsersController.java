package dm848.controller;

import dm848.SearchCriteria;
import dm848.dto.Comment;
import dm848.dto.Image;
import dm848.dto.User;
import dm848.service.WebCommentService;
import dm848.service.WebImageService;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
	@Autowired
	protected WebCommentService commentService;
	@Autowired
	protected WebImageService imageService;

	protected Logger logger = Logger.getLogger(WebUsersController.class
			.getName());

	public WebUsersController(WebUsersService usersService) {
		this.usersService = usersService;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields("userId", "searchText");
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
		List<Comment> comments = commentService.byUserName(user.getUserName());
		List<Image> images = imageService.findByUserName(user.getUserName());

		logger.info("web-service byUserName() found: " + user);
		model.addAttribute("user", user);
		model.addAttribute("comments", comments);
		model.addAttribute("images", images);
		return "users/user";
	}

    @RequestMapping("/users/name/{text}")
    public String byName(Model model, @PathVariable("text") String name) {
        logger.info("web-service byName() invoked: " + name);

        List<User> users = usersService.byNameContains(name);
        logger.info("web-service byName() found: " + users);
        model.addAttribute("search", name);
        if (users != null)
            model.addAttribute("users", users);
        return "users/users";
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
			return "users/userSearch";

		String userName = criteria.getUserName();
		if (StringUtils.hasText(userName)) {
			return byUserName(model, userName);
		} else {
			String searchText = criteria.getSearchText();
			return byName(model, searchText);
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

	@RequestMapping(value = "/users/active/{date}")
	public String findActive(Model model, @PathVariable("date") String date) {
		logger.info("web-service findActive() invoked.");

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = null;
        List<User> activeUsers = new ArrayList<>();

        try {
            date1 = format.parse(date);
        } catch (ParseException e) {
            return "users/all"; //fail silently
        }

        List<User> users = usersService.findAll();


        for (User user : users) {

            Date date2 = null;

            try {
                date2 = format.parse(user.getLastActive());

                if (date1.compareTo(date2) < 0) {
                    activeUsers.add(user);
                }
            } catch (ParseException ignored) {}
        }

        Collections.sort(activeUsers, (o1, o2) -> o2.getLastActive().compareTo(o1.getLastActive()));


		logger.info("web-service findActive() found: " + activeUsers);
		if (activeUsers != null)
			model.addAttribute("users", activeUsers);
		return "users/all";
	}
}
