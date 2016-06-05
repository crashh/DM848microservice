package io.dm848.microservices.webserverservice.controller;


import io.dm848.microservices.webserverservice.dto.Video;
import io.dm848.microservices.webserverservice.service.WebUsersService;
import io.dm848.microservices.webserverservice.service.WebVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

        /*
        * Spotlight is just 3 randomy chosen videos, ince this is just
        * to demo actual methods a microservice might offer.
        */

        Random rand = new Random();

        List<Video> spotlights = new ArrayList<>();
		List<Video> videos = videoService.findAll();

        spotlights.add(videos.get(rand.nextInt(videos.size()-1)));
        spotlights.add(videos.get(rand.nextInt(videos.size()-1)));
        spotlights.add(videos.get(rand.nextInt(videos.size()-1)));

        for (Video video: spotlights) {
            String embeddedLink = video.getLink().replace("watch?v=", "embed/");
            video.setEmbeddedLink(embeddedLink + "?autoplay=0");
        }

		logger.info("web-service ajax/spotlight() found: " + spotlights);

		return spotlights;
	}

    @RequestMapping(value = "/videos/ajax/newest", method = RequestMethod.GET)
    public @ResponseBody // <- Enables ajax response type.
    List<Video> newest() {
        logger.info("web-service ajax/newest() invoked.");

        /*
        * Not really the newest videos, just the 6 first from the query, since this is just
        * to demo actual methods a microservice might offer.
        */

        List<Video> videos = videoService.findAll();
        videos = videos.subList(0, 6); //Limit this to 6 videos.

        for (Video video: videos) {
            String embeddedLink = video.getLink().replace("watch?v=", "embed/");
            video.setEmbeddedLink(embeddedLink + "?autoplay=0");
        }

        logger.info("web-service ajax/newest() found: " + videos);

        return videos;
    }

    @RequestMapping(value = "/videos/ajax/all", method = RequestMethod.GET)
    public @ResponseBody // <- Enables ajax response type.
    List<Video> findAll() {
        logger.info("web-service ajax/findAll() invoked.");

        /*
        * Not really the newest videos, just the 6 first from the query, since this is just
        * to demo actual methods a microservice might offer.
        */

        List<Video> videos = videoService.findAll();

        for (Video video: videos) {
            String embeddedLink = video.getLink().replace("watch?v=", "embed/");
            video.setEmbeddedLink(embeddedLink + "?autoplay=0");
        }

        logger.info("web-service ajax/findAll() found: " + videos);

        return videos;
    }
}
