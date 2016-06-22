package dm848.images;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Repository for Video data implemented using Spring Data JPA.
 */
public interface ImageRepository extends Repository<Image, Long> {

	/**
	 * Find an image with the specified image id.
	 *
	 * @param id
	 * @return The image if found, null otherwise.
	 */
	public Image findById(Long id);

	public List<Image> findByUserName(String username);

	/**
	 * Find all image in the database
	 *
	 * @return The list of all images in the database.
     */
	public List<Image> findAll();

	/**
	 * Fetch the number of videos known to the system.
	 * 
	 * @return The number of videos.
	 */
	@Query("SELECT count(*) from Image")
	public int countImages();
}
