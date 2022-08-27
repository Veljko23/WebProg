package services;

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
import javax.ws.rs.core.Response;

import beans.Comment;
import beans.Post;
import beans.User;
import dao.PostDAO;
import dto.CommentDTO;
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
	public Response newPostCreate(PostDTO postDTO, @Context HttpServletRequest request) {
		Post post = new Post(postDTO);
		PostDAO dao = PostDAO.getInstance();
		
		User loggedUser = (User) request.getSession().getAttribute("user");
		
		
		dao.save(post, loggedUser);
		
		return Response.status(200).build();
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
	public PostDTO deletePost(@PathParam("id") int id) {
		PostDAO dao = PostDAO.getInstance();
		return new PostDTO(dao.delete(id));
	}
	
	@POST
	@Path("/setPost")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void setPost(PostDTO postDTO, @Context HttpServletRequest request ) {
		request.getSession().setAttribute("postId", postDTO.getId());
		
		return;
	}
	
	@GET
	@Path("/getPost")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public PostDTO getPost( @Context HttpServletRequest request) {
		if(request.getSession().getAttribute("postId") == null) {
			return null;
		}
		int postId = (int)request.getSession().getAttribute("postId");
		Post post = PostDAO.getInstance().findPost(postId);
		
		return new PostDTO(post);
	}
	
	@GET
	@Path("/getCommentsForPost/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<CommentDTO> getCommentsPost(@PathParam("id") int id) {
		
		Post post = PostDAO.getInstance().findPost(id);
		
		ArrayList<CommentDTO> dtos = new ArrayList<CommentDTO>();
		for(Comment comment : post.getComments()) {
			dtos.add(new CommentDTO(comment));
		}
		
		
		return dtos;
	}
	
}
