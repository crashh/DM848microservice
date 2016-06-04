package io.dm848.microservices.videoservice.videos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Repository for Video data implemented using Spring Data JPA.
 */
public interface VideoRepository extends Repository<Video, Long> {

	/**
	 * Find an video with the specified video id.
	 *
	 * @param id
	 * @return The video if found, null otherwise.
	 */
	public Video findById(Long id);


	/**
	 * Find all videos in the database
	 *
	 * @return The list of all videos in the database.
     */
	public List<Video> findAll();

	/**
	 * Fetch the number of videos known to the system.
	 * 
	 * @return The number of videos.
	 */
	@Query("SELECT count(*) from Video")
	public int countVideos();
}
