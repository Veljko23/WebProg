package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
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

import beans.Post;
import dao.PostDAO;
import dto.PostDTO;

@Path("/posts")
public class PostService {
	
	@Context
	ServletContext ctx;
	
	public PostService() {
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira više puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
	    String contextPath = ctx.getRealPath("");
		ProjectInit.getInstance(contextPath);
		
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<PostDTO> getPosts() {
		PostDAO dao = PostDAO.getInstance();
		ArrayList<Post> posts = new ArrayList<Post>(dao.findAll());
		ArrayList<PostDTO> postsDTO = new ArrayList<PostDTO>();
		
		for(Post post: posts) {
			postsDTO.add(new PostDTO(post));
		}
		
		return postsDTO;
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Post newPost(Post post) {
		PostDAO dao = PostDAO.getInstance();
		return dao.save(post);
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Post findOne(@PathParam("id") int id) {
		PostDAO dao = PostDAO.getInstance();
		return dao.findPost(id);
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Post search(@QueryParam("name") String text) {
		PostDAO dao = PostDAO.getInstance();
		return dao.findAll().stream()
				.filter(post -> post.getText().equals(text))
				.findFirst()
				.orElse(null);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Post changeOne(Post post, @PathParam("id") String id) {
		PostDAO dao = PostDAO.getInstance();
		return dao.change(post);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Post deletePost(@PathParam("id") int id) {
		PostDAO dao = PostDAO.getInstance();
		return dao.delete(id);
	}
	
}
