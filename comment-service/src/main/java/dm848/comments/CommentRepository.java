package dm848.comments;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Repository for Comment data implemented using Spring Data JPA.
 */
public interface CommentRepository extends Repository<Comment, Long> {

	/**
	 * Find an comment with the specified comment id.
	 *
	 * @param id
	 * @return The comment if found, null otherwise.
	 */
	public Comment findById(Long id);

	/**
	 * Find all comments in the database
	 *
	 * @return The list of all comments in the database.
     */
	public List<Comment> findAll();

	/**
	 * Fetch the number of comments known to the system.
	 * 
	 * @return The number of comments.
	 */
	@Query("SELECT count(*) from Comment")
	public int countComments();
}
