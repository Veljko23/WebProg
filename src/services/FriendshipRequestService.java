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

import beans.FriendshipRequest;
import dao.FriendShipRequestDAO;

@Path("/requests")
public class FriendshipRequestService {
	
	@Context
	ServletContext ctx;
	
	public FriendshipRequestService() {
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira više puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("requestDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("requestDAO", new FriendShipRequestDAO(contextPath));
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<FriendshipRequest> getFriendshipRequests() {
		FriendShipRequestDAO dao = (FriendShipRequestDAO) ctx.getAttribute("requestDAO");
		return dao.findAll();
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public FriendshipRequest newFriendshipRequest(FriendshipRequest request) {
		FriendShipRequestDAO dao = (FriendShipRequestDAO) ctx.getAttribute("requestDAO");
		return dao.save(request);
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public FriendshipRequest findOne(@PathParam("id") int id) {
		FriendShipRequestDAO dao = (FriendShipRequestDAO) ctx.getAttribute("requestDAO");
		return dao.findFriendshipRequest(id);
	}
	
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public FriendshipRequest search(@QueryParam("name") String text) {
		FriendShipRequestDAO dao = (FriendShipRequestDAO) ctx.getAttribute("requestDAO");
		return dao.findAll().stream()
				.filter(request -> request.getStatus().equals(text))
				.findFirst()
				.orElse(null);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public FriendshipRequest changeOne(FriendshipRequest request, @PathParam("id") String id) {
		FriendShipRequestDAO dao = (FriendShipRequestDAO) ctx.getAttribute("requestDAO");
		return dao.change(request);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public FriendshipRequest deleteFriendshipRequest(@PathParam("id") int id) {
		FriendShipRequestDAO dao = (FriendShipRequestDAO) ctx.getAttribute("requestDAO");
		return dao.delete(id);
	}
	
}
