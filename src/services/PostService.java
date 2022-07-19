package services;

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

@Path("/posts")
public class PostService {
	
	@Context
	ServletContext ctx;
	
	public PostService() {
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vi�e puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("postDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("postDAO", new PostDAO(contextPath));
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Post> getPosts() {
		PostDAO dao = (PostDAO) ctx.getAttribute("postDAO");
		return dao.findAll();
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Post newPost(Post post) {
		PostDAO dao = (PostDAO) ctx.getAttribute("postDAO");
		return dao.save(post);
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Post findOne(@PathParam("id") int id) {
		PostDAO dao = (PostDAO) ctx.getAttribute("postDAO");
		return dao.findPost(id);
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Post search(@QueryParam("name") String text) {
		PostDAO dao = (PostDAO) ctx.getAttribute("postDAO");
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
		PostDAO dao = (PostDAO) ctx.getAttribute("postDAO");
		return dao.change(post);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Post deletePost(@PathParam("id") int id) {
		PostDAO dao = (PostDAO) ctx.getAttribute("postDAO");
		return dao.delete(id);
	}
	
}
