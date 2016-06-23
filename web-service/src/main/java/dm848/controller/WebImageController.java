package dm848.controller;


import dm848.dto.Comment;
import dm848.dto.Image;
import dm848.service.WebCommentService;
import dm848.service.WebImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.Logger;

/**
 * Client controller, fetches Video info from the microservice via
 * {@link WebImageService}.
 */
@Controller
public class WebImageController {

	@Autowired
	protected WebImageService imageService;
    @Autowired
    protected WebCommentService commentService;

	protected Logger logger = Logger.getLogger(WebImageController.class
			.getName());

	public WebImageController(WebImageService imageService) {
		this.imageService = imageService;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields("imageId", "searchText");
	}

	@RequestMapping("/images")
	public String goHome() {
		return "index";
	}

	@RequestMapping("/images/{id}")
	public String byImageId(Model model, @PathVariable("id") String id) {

		logger.info("web-service byImageId() invoked: " + id);

		Image image = imageService.findById(id);
        List<Comment> comments = commentService.byVideoId(image.getId());

		logger.info("web-service byImageId() found: " + image);
		model.addAttribute("image", image);
        model.addAttribute("comments", comments);
		return "images/image";
	}

	@RequestMapping("/images/all")
	public String findAll(Model model) {
		logger.info("web-service findAll() invoked.");

        // We handle this using ajax.

        return "images/all";
	}

	@RequestMapping("/images/newest")
	public String findNewest(Model model) {
		logger.info("web-service findNewest() invoked.");

		// We handle this using ajax.

		return "images/newest";
	}

	@RequestMapping("/images/commented")
	public String findMostCommented(Model model) {
		logger.info("web-service findNewest() invoked.");

		List<Image> images = imageService.findAll();
		List<Comment> comments = commentService.findAll();

		Map<Long, Integer> commentCount = new HashMap<>();

		for (Comment comment: comments) {
			if (commentCount.containsKey(comment.getVideoId())) {
				commentCount.put(comment.getVideoId(), commentCount.get(comment.getVideoId()) + 1);
			} else {
				commentCount.put(comment.getVideoId(), 1);
			}
		}

		for (Image img: images) {
            if (commentCount.containsKey(img.getId())) {
                img.setCount(commentCount.get(img.getId()));
            } else {
                img.setCount(0);
            }
		}

		model.addAttribute("images", images);

		return "images/commented";
	}

    @RequestMapping(value = "/images/ajax/all", method = RequestMethod.GET)
    public @ResponseBody	// <- Enables ajax response type.
    List<Image> findAll() {
        logger.info("web-service ajax/findAll() invoked.");

        List<Image> images = imageService.findAll();

        for (Image image : images) {
            String embeddedLink = image.getLink();
            image.setEmbeddedLink(embeddedLink);
        }

        logger.info("web-service ajax/findAll() found: " + images.size());

        return images;
    }

	@RequestMapping(value = "/images/ajax/spotlight", method = RequestMethod.GET)
	public @ResponseBody	// <- Enables ajax response type.
    List<Image> spotlight() {
		logger.info("web-service ajax/spotlight() invoked.");

        /*
        * Spotlight is just 3 randomly chosen images, since this is just
        * to demo actual methods a microservice might offer.
        */
		List<Image> spotlights = imageService.findSpotlight();

        for (Image image : spotlights) {
            String embeddedLink = image.getLink();
            image.setEmbeddedLink(embeddedLink);
        }

		logger.info("web-service ajax/spotlight() found: " + spotlights.size());

		return spotlights;
	}

    @RequestMapping(value = "/images/ajax/newest", method = RequestMethod.GET)
    public @ResponseBody    // <- Enables ajax response type.
    List<Image> newest(@RequestParam("amount") String amount) {
        logger.info("web-service ajax/newest() invoked with amount "+amount+".");

        List<Image> images = imageService.findAll();
        Collections.sort(images, (o1, o2) -> o2.getDate().compareTo(o1.getDate()));

		if (!amount.equals("all")) {
			images = images.subList(0, Integer.parseInt(amount));
		}

        for (Image image : images) {
            String embeddedLink = image.getLink();
            image.setEmbeddedLink(embeddedLink);
        }

        logger.info("web-service ajax/newest() found: " + images.size());

        return images;
    }


}
