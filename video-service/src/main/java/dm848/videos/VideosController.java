package dm848.videos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * A RESTFul controller for accessing controller information.
 */
@RestController
public class VideosController {

	protected Logger logger = Logger.getLogger(VideosController.class
			.getName());
	protected VideoRepository videoRepository;

	/**
	 * Create an instance plugging in the respository of Videos.
	 * 
	 * @param videoRepository
	 *            An controller repository implementation.
	 */
	@Autowired
	public VideosController(VideoRepository videoRepository) {
		this.videoRepository = videoRepository;

		logger.info("VideoRepository says system has "
				+ videoRepository.countVideos() + " videos");
	}

	/**
	 * Fetch an video with the specified video id.
	 * 
	 * @param id
	 *            A numeric, 9 digit controller videoId.
	 * @return The controller if found.
	 */
	@RequestMapping("/videos/{id}")
	public Video byVideoId(@PathVariable("id") String id) {

		logger.info("video-service byVideoId() invoked: " + id);
		Video video = videoRepository.findById(Long.parseLong(id));
		logger.info("video-service byVideoId() found: " + video);

		if (video == null)
			return null;
		else {
			return video;
		}
	}


    /**
	 * Fetch all Videos
     *
     * @return a list containing all videos.
     */
	@RequestMapping("/videos/all/")
	public List<Video> findAll() {

		logger.info("video-service findAll() invoked: "
				+ videoRepository.getClass().getName());

		List<Video> videos = videoRepository.findAll();

		logger.info("video-service findAll() found: " + videos.size());

		if (videos == null || videos.size() == 0)
			return new ArrayList<>();
		else {
			return videos;
		}
	}
}
