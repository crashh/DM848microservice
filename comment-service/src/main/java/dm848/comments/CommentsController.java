package dm848.comments;

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
public class CommentsController {

	protected Logger logger = Logger.getLogger(CommentsController.class
			.getName());
	protected CommentRepository commentRepository;

	/**
	 * Create an instance plugging in the repository of Comments.
	 * 
	 * @param commentRepository
	 *            An controller repository implementation.
	 */
	@Autowired
	public CommentsController(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;

		logger.info("CommentRepository says system has "
				+ commentRepository.countComments() + " comments");
	}

	/**
	 * Fetch an comment with the specified comment id.
	 * 
	 * @param id
	 *            A numeric, 9 digit controller commentId.
	 * @return The controller if found.
	 */
	@RequestMapping("/comments/{id}")
	public Comment byCommentId(@PathVariable("id") String id) {

		logger.info("comment-service byCommentId() invoked: " + id);
		Comment comment = commentRepository.findById(Long.parseLong(id));
		logger.info("comment-service byCommentId() found: " + comment);

		if (comment == null)
			return null;
		else {
			return comment;
		}
	}

	@RequestMapping("/comments/user/{username}")
	public List<Comment> byUserName(@PathVariable("username") String username) {

		logger.info("comment-service byCommentId() invoked: " + username);
		List<Comment> comment = commentRepository.findByUserId(username);
		logger.info("comment-service byCommentId() found: " + comment);

		if (comment == null)
			return null;
		else {
			return comment;
		}
	}

	@RequestMapping("/comments/image/{id}")
	public List<Comment> byVideoId(@PathVariable("id") Long id) {

		logger.info("comment-service byCommentId() invoked: " + id);
		List<Comment> comment = commentRepository.findByVideoId(id);
		logger.info("comment-service byCommentId() found: " + comment);

		if (comment == null)
			return null;
		else {
			return comment;
		}
	}


    /**
	 * Fetch all comments
     *
     * @return a list containing all comments.
     */
	@RequestMapping("/comments/all/")
	public List<Comment> findAll() {

		logger.info("comment-service findAll() invoked: "
				+ commentRepository.getClass().getName());

		List<Comment> comments = commentRepository.findAll();

		logger.info("comment-service findAll() found: " + comments.size());

		if (comments == null || comments.size() == 0)
			return new ArrayList<>();
		else {
			return comments;
		}
	}
}
