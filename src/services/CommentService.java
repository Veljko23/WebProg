package services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Comment;
import beans.Post;
import beans.User;
import dao.CommentDAO;
import dao.PostDAO;
import dto.CommentDTO;

@Path("/comments")
public class CommentService {
	
	@Context
	ServletContext ctx;
	
	public CommentService() {
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vi�e puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
	    String contextPath = ctx.getRealPath("");
		ProjectInit.getInstance(contextPath);
		
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<CommentDTO> getComments() {
		CommentDAO dao = CommentDAO.getInstance();
		ArrayList<Comment> comments = new ArrayList<Comment>(dao.findAll());
		ArrayList<CommentDTO> commentsDTO = new ArrayList<CommentDTO>();
		
		for(Comment comment : comments) {
			commentsDTO.add(new CommentDTO(comment));
		}
		
		return commentsDTO;
	}
	
	@POST
	@Path("/{postId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CommentDTO newComment(CommentDTO commentDTO, @PathParam("postId") int id, @Context HttpServletRequest request) {
		CommentDAO dao = CommentDAO.getInstance();
		
		User user = (User) request.getSession().getAttribute("user");
		if(user == null) {
			return null;
		}
		
		Comment comment = new Comment(commentDTO);
		comment.setCommentDate(LocalDate.now());
		comment.setUser(user);
		
		Post post = PostDAO.getInstance().findPost(id);
		comment.setPost(post);
		post.getComments().add(comment);
		
		return new CommentDTO(dao.save(comment));
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Comment findOne(@PathParam("id") int id) {
		CommentDAO dao = CommentDAO.getInstance();
		return dao.findComment(id);
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Comment search(@QueryParam("name") String text) {
		CommentDAO dao = CommentDAO.getInstance();
		return dao.findAll().stream()
				.filter(comment -> comment.getText().equals(text))
				.findFirst()
				.orElse(null);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Comment changeOne(Comment comment, @PathParam("id") String id) {
		CommentDAO dao = CommentDAO.getInstance();
		return dao.change(comment);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public CommentDTO deleteComment(@PathParam("id") int id) {
		CommentDAO dao = CommentDAO.getInstance();
		return new CommentDTO(dao.delete(id));
	}
	
}
