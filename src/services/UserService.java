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


import beans.User;
import dao.UserDAO;
import dto.UserDTO;

@Path("/users")
public class UserService {
	
	@Context
	ServletContext ctx;
	
	public UserService() {
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@UserConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira više puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
	    String contextPath = ctx.getRealPath("");
		ProjectInit.getInstance(contextPath);
		
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<UserDTO> getUsers() {
		UserDAO dao = UserDAO.getInstance();
		ArrayList<User> users = new ArrayList<User>(dao.findAll());
		ArrayList<UserDTO> usersDTO = new ArrayList<UserDTO>();
		
		for(User user: users) {
			usersDTO.add(new UserDTO(user));
		}
		
		return usersDTO;
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User newUser(User user) {
		UserDAO dao = UserDAO.getInstance();
		
		boolean exists = dao.existsUsername(user.getUsername());
		if(exists) {
			return null;
		}
		
		return dao.save(user);
	}
	
	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerUser(UserDTO userDTO) {
		User user = new User(userDTO);
		UserDAO dao = UserDAO.getInstance();
		boolean exists = dao.existsUsername(user.getUsername());
		if(exists) {
			return Response.status(400).entity("Invalid username and/or password").build();
		}
		dao.save(user);
		return Response.status(200).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User findOne(@PathParam("id") int id) {
		UserDAO dao = UserDAO.getInstance();
		return dao.findUser(id);
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public User search(@QueryParam("name") String text) {
		UserDAO dao = UserDAO.getInstance();
		return dao.findAll().stream()
				.filter(user -> user.getName().equals(text))
				.findFirst()
				.orElse(null);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserDTO changeOne(UserDTO userDTO, @PathParam("id") String id) {
		User user = new User(userDTO);
		UserDAO dao = UserDAO.getInstance();
		return new UserDTO(dao.change(user));
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User deleteUser(@PathParam("id") int id) {
		UserDAO dao = UserDAO.getInstance();
		return dao.delete(id);
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(User user, @Context HttpServletRequest request) {
		UserDAO dao = UserDAO.getInstance();
		User loggedUser = dao.findByUsernamePassword(user.getUsername(), user.getPassword());
		if (loggedUser == null) {
			return Response.status(400).entity("Invalid username and/or password").build();
		}
		request.getSession().setAttribute("user", loggedUser);
		return Response.status(200).build();
	}
	
	
	@POST
	@Path("/logout")
	public void logout(@Context HttpServletRequest request) {
		request.getSession().invalidate();
	}
	
	@GET
	@Path("/currentUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserDTO login(@Context HttpServletRequest request) {
		User user =  (User) request.getSession().getAttribute("user");
		if(user == null)
			return null;
		UserDTO userDTO = new UserDTO(user);
		return userDTO;
	}
	
	@GET
	@Path("/mutualFriends")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<User> search(@Context HttpServletRequest request, @QueryParam("id") int id) {
		UserDAO dao = UserDAO.getInstance();
		User loggedUser = (User) request.getSession().getAttribute("user");
		ArrayList<User> mutuals = dao.getMutualFriends(loggedUser.getId(), id);
		
		return mutuals;
	}
	
//	@DELETE
//	@Path("/{id}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public User removeFriend(@Context HttpServletRequest request, @PathParam("id") int id) {
//		UserDAO dao = UserDAO.getInstance();
//		User loggedUser = (User) request.getSession().getAttribute("user");
//		User friend = dao.removeFriend(loggedUser.getId(), id);
//		return friend;
//	} ========= treba promeniti Path, metoda je dobra!
	
}
