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

import beans.FriendshipRequest;
import dao.FriendShipRequestDAO;
import dto.FriendshipRequestDTO;

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
	    String contextPath = ctx.getRealPath("");
		ProjectInit.getInstance(contextPath);
		
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<FriendshipRequestDTO> getFriendshipRequests() {
		FriendShipRequestDAO dao = FriendShipRequestDAO.getInstance();
		ArrayList<FriendshipRequest> requests = new ArrayList<FriendshipRequest>(dao.findAll());
		ArrayList<FriendshipRequestDTO> requestsDTO = new ArrayList<FriendshipRequestDTO>();
		
		for(FriendshipRequest request : requests) {
			requestsDTO.add(new FriendshipRequestDTO(request));
		}
		
		return requestsDTO;
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public FriendshipRequestDTO newFriendshipRequest(FriendshipRequestDTO requestDTO) {
		FriendShipRequestDAO dao = FriendShipRequestDAO.getInstance();
		
		FriendshipRequest request = new FriendshipRequest(requestDTO);
		
		return new FriendshipRequestDTO(dao.save(request));
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public FriendshipRequest findOne(@PathParam("id") int id) {
		FriendShipRequestDAO dao = FriendShipRequestDAO.getInstance();
		return dao.findFriendshipRequest(id);
	}
	
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public FriendshipRequest search(@QueryParam("name") String text) {
		FriendShipRequestDAO dao = FriendShipRequestDAO.getInstance();
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
		FriendShipRequestDAO dao = FriendShipRequestDAO.getInstance();
		return dao.change(request);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public FriendshipRequest deleteFriendshipRequest(@PathParam("id") int id) {
		FriendShipRequestDAO dao = FriendShipRequestDAO.getInstance();
		return dao.delete(id);
	}
	
	
	
}
