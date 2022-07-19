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

import beans.DirectMessage;
import dao.DirectMessageDAO;

@Path("/messages")
public class DirectMessageService {
	
	@Context
	ServletContext ctx;
	
	public DirectMessageService() {
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira više puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("messageDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("messageDAO", new DirectMessageDAO(contextPath));
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<DirectMessage> getDirectMessages() {
		DirectMessageDAO dao = (DirectMessageDAO) ctx.getAttribute("messageDAO");
		return dao.findAll();
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DirectMessage newDirectMessage(DirectMessage message) {
		DirectMessageDAO dao = (DirectMessageDAO) ctx.getAttribute("messageDAO");
		return dao.save(message);
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public DirectMessage findOne(@PathParam("id") int id) {
		DirectMessageDAO dao = (DirectMessageDAO) ctx.getAttribute("messageDAO");
		return dao.findDirectMessage(id);
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public DirectMessage search(@QueryParam("name") String text) {
		DirectMessageDAO dao = (DirectMessageDAO) ctx.getAttribute("messageDAO");
		return dao.findAll().stream()
				.filter(message -> message.getMessageContext().equals(text))
				.findFirst()
				.orElse(null);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DirectMessage changeOne(DirectMessage message, @PathParam("id") String id) {
		DirectMessageDAO dao = (DirectMessageDAO) ctx.getAttribute("messageDAO");
		return dao.change(message);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public DirectMessage deleteDirectMessage(@PathParam("id") int id) {
		DirectMessageDAO dao = (DirectMessageDAO) ctx.getAttribute("messageDAO");
		return dao.delete(id);
	}
	
}
