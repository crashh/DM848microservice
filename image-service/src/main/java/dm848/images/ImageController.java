package dm848.images;

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
public class ImageController {

	protected Logger logger = Logger.getLogger(ImageController.class
			.getName());
	protected ImageRepository imageRepository;

	/**
	 * Create an instance plugging in the respository of Videos.
	 * 
	 * @param imageRepository
	 *            An controller repository implementation.
	 */
	@Autowired
	public ImageController(ImageRepository imageRepository) {
		this.imageRepository = imageRepository;

		logger.info("Imagerepository says system has "
				+ imageRepository.countImages() + " images");
	}

	/**
	 * Fetch an video with the specified video id.
	 * 
	 * @param id
	 *            A numeric, 9 digit controller videoId.
	 * @return The controller if found.
	 */
	@RequestMapping("/images/{id}")
	public Image byVideoId(@PathVariable("id") String id) {

		logger.info("image-service byImageId() invoked: " + id);
		Image image = imageRepository.findById(Long.parseLong(id));
		logger.info("image-service byImageId() found: " + image);

		if (image == null)
			return null;
		else {
			return image;
		}
	}


    /**
	 * Fetch all Images
     *
     * @return a list containing all images.
     */
	@RequestMapping("/images/all/")
	public List<Image> findAll() {

		logger.info("image-service findAll() invoked: "
				+ imageRepository.getClass().getName());

		List<Image> images = imageRepository.findAll();

		logger.info("image-service findAll() found: " + images.size());

		if (images == null || images.size() == 0)
			return new ArrayList<>();
		else {
			return images;
		}
	}

	@RequestMapping("/images/user/{username}")
	public List<Image> findByUserName(@PathVariable("username") String username) {

		logger.info("image-service findByUserName() invoked: "
				+ imageRepository.getClass().getName());

		List<Image> comments = imageRepository.findByUserName(username);

		logger.info("image-service findByUserName() found: " + comments.size());

		if (comments == null || comments.size() == 0)
			return new ArrayList<>();
		else {
			return comments;
		}
	}
}
