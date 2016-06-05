package io.dm848.microservices.webserverservice.controller;

import io.dm848.microservices.webserverservice.SearchCriteria;
import io.dm848.microservices.webserverservice.dto.User;
import io.dm848.microservices.webserverservice.dto.Video;
import io.dm848.microservices.webserverservice.service.WebUsersService;
import io.dm848.microservices.webserverservice.service.WebVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * Client controller, fetches User info from the microservice via
 * {@link WebUsersService}.
 */
@Controller
public class WebVideoController {

	@Autowired
	protected WebVideoService videoService;

	protected Logger logger = Logger.getLogger(WebVideoController.class
			.getName());

	public WebVideoController(WebVideoService videoService) {
		this.videoService = videoService;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields("videoId", "searchText");
	}

	@RequestMapping("/videos")
	public String goHome() {
		return "index";
	}

	@RequestMapping("/videos/{id}")
	public String byVideoId(Model model,
			@PathVariable("id") String id) {

		logger.info("web-service byVideoId() invoked: " + id);

		Video video = videoService.findById(id);

		logger.info("web-service byVideoId() found: " + id);
		model.addAttribute("video", video);
		return "videos/video";
	}

	@RequestMapping(value = "/videos/all/")
	public String findAll(Model model) {
		logger.info("web-service findAll() invoked.");

		List<Video> videos = videoService.findAll();

		logger.info("web-service findAll() found: " + videos);
        model.addAttribute("search", "all");
        if (videos != null)
            model.addAttribute("videos", videos);
        return "videos/all";
	}

	@RequestMapping(value = "/videos/ajax/spotlight", method = RequestMethod.GET)
	public @ResponseBody // <- Enables ajax response type.
    List<Video> spotlight() {
		logger.info("web-service ajax/spotlight() invoked.");

		List<Video> videos = videoService.findAll();
        videos = videos.subList(0, 3); //Only need 3!

		logger.info("web-service ajax/spotlight() found: " + videos);

		return videos;

	}
}
