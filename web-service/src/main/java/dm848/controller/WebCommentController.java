package dm848.controller;


import dm848.dto.Comment;
import dm848.service.WebCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * Client controller, fetches Comment info from the microservice via
 * {@link WebCommentService}.
 */
@Controller
public class WebCommentController {

	@Autowired
	protected WebCommentService commentService;

	protected Logger logger = Logger.getLogger(WebCommentController.class
			.getName());

	public WebCommentController(WebCommentService commentService) {
		this.commentService = commentService;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields("imageId", "searchText");
	}

	@RequestMapping("/comment")
	public String goHome() {
		return "index";
	}

	@RequestMapping("/comment/{id}")
	public String byImageId(Model model, @PathVariable("id") String id) {

		logger.info("web-service byCommentId() invoked: " + id);

		Comment comment = commentService.findById(id);

		logger.info("web-service byImageId() found: " + comment);
		model.addAttribute("comment", comment);
		return "comments/comment";
	}

	@RequestMapping("/comment/all")
	public String findAll(Model model) {
		logger.info("web-service findAll() invoked.");

		List<Comment> comments = commentService.findAll();

		logger.info("web-service findAll() found: " + comments);

		if (comments != null) {
			model.addAttribute("comments", comments);
		}

        return "comments/all";
	}


    @RequestMapping(value = "/comment/ajax/byUser", method = RequestMethod.GET)
    public @ResponseBody	// <- Enables ajax response type.
    List<Comment> findAll(@RequestParam("username") String username) {
        logger.info("web-service ajax/byUser() invoked with username "+username+".");

        List<Comment> comments = commentService.byUserName(username);

        logger.info("web-service ajax/byUser() found: " + comments.size());

        return comments;
    }

	@RequestMapping(value = "/comment/ajax/byVideo", method = RequestMethod.GET)
    public @ResponseBody    // <- Enables ajax response type.
    List<Comment> newest(@RequestParam("id") String id) {
        logger.info("web-service ajax/byVideo() invoked with id "+id+".");

		try {
        	List<Comment> comments = commentService.byVideoId(Long.parseLong(id));

			logger.info("web-service ajax/byVideo() found: " + comments.size());

			return comments;
		} catch (Exception e) {}

		logger.info("web-service ajax/byVideo() failed due to parsing error.");
		return null;
    }


}
