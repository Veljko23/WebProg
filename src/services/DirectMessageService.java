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

import beans.DirectMessage;
import beans.User;
import dao.DirectMessageDAO;
import dao.UserDAO;
import dto.DirectMessageDTO;
import dto.UserDTO;
import enums.Role;

@Path("/messages")
public class DirectMessageService {
	
	@Context
	ServletContext ctx;
	
	public DirectMessageService() {
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
	public Collection<DirectMessageDTO> getDirectMessages() {
		DirectMessageDAO dao = DirectMessageDAO.getInstance();
		ArrayList<DirectMessage> messages = new ArrayList<DirectMessage>(dao.findAll());
		ArrayList<DirectMessageDTO> messagesDTO = new ArrayList<DirectMessageDTO>();
		
		for(DirectMessage message : messages) {
			messagesDTO.add(new DirectMessageDTO(message));
		}
		
		return messagesDTO;
	}
	
	@POST
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DirectMessageDTO newDirectMessage(DirectMessageDTO messageDTO, @PathParam("userId") int id, @Context HttpServletRequest request) {
		
		DirectMessageDAO dao = DirectMessageDAO.getInstance();
		
		User user = (User) request.getSession().getAttribute("user");
		if(user == null) {
			return null;
		}
		
		DirectMessage message = new DirectMessage(messageDTO);
		message.setMessageDate(LocalDate.now());
		message.setSender(user);
		
		if(message.getSender().getRole() == Role.ADMINISTRATOR) {
			message.setMessageContext("|ADMIN " + message.getSender().getName() + "|:" + message.getMessageContext());
		}
		
		User u = UserDAO.getInstance().findUser(id);
		message.setReceiver(u);
		
		return new DirectMessageDTO(dao.save(message));
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public DirectMessage findOne(@PathParam("id") int id) {
		DirectMessageDAO dao = DirectMessageDAO.getInstance();
		return dao.findDirectMessage(id);
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public DirectMessage search(@QueryParam("name") String text) {
		DirectMessageDAO dao = DirectMessageDAO.getInstance();
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
		DirectMessageDAO dao = DirectMessageDAO.getInstance();
		return dao.change(message);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public DirectMessage deleteDirectMessage(@PathParam("id") int id) {
		DirectMessageDAO dao = DirectMessageDAO.getInstance();
		return dao.delete(id);
	}
	
	@GET
	@Path("/chat/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<DirectMessageDTO> senderMessage(@PathParam("id") int id, @Context HttpServletRequest request) {
		
		User user =  (User) request.getSession().getAttribute("user");
		if(user == null)
			return null;
		
		Collection<DirectMessage> messages = DirectMessageDAO.getInstance().findAll();
		ArrayList<DirectMessageDTO> userMessages = new ArrayList<DirectMessageDTO>();
		for(DirectMessage m: messages) {
			if(m.getSender().getId() == id || m.getReceiver().getId() == id) {
				userMessages.add(new DirectMessageDTO(m));
			}
		}		
		
		return userMessages;
	}
	
	@GET
    @Path("/getAllUserMessageFriends")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ArrayList<UserDTO> getAllUserMessageFriends(@Context HttpServletRequest request) {

        DirectMessageDAO dao = DirectMessageDAO.getInstance();

        User user = (User) request.getSession().getAttribute("user");
        if(user == null) {
            return null;
        }
        ArrayList<User> friends = dao.getAllUserMessageFriends(user.getId());

        ArrayList<UserDTO> usersDTO = new ArrayList<UserDTO>();
        for(User userIter : friends) {
            usersDTO.add(new UserDTO(userIter));
        }
        return usersDTO;
    }
	
	@GET
	@Path("/chat")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<DirectMessageDTO> chat(@Context HttpServletRequest request) {
		DirectMessageDAO dao = DirectMessageDAO.getInstance();
		if(request.getSession().getAttribute("userId") == null || request.getSession().getAttribute("user") == null) {
			return null;
		}
		int userId = (int)request.getSession().getAttribute("userId");
		User user = UserDAO.getInstance().findUser(userId);
		
		User loggedUser = (User) request.getSession().getAttribute("user");
		ArrayList<DirectMessage> messagesFound = dao.getChat(user.getId(), loggedUser.getId());
		ArrayList<DirectMessageDTO> messagesDTO = new ArrayList<DirectMessageDTO>();
		for(DirectMessage message : messagesFound) {
			messagesDTO.add(new DirectMessageDTO(message));
		}
		
		return messagesDTO;
	}
	
	@POST
	@Path("/sendMessage")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DirectMessageDTO sendMessage(@Context HttpServletRequest request, DirectMessageDTO messageDTO) {
		DirectMessageDAO dao = DirectMessageDAO.getInstance();
		if(request.getSession().getAttribute("userId") == null || request.getSession().getAttribute("user") == null) {
			return null;
		}
		int userId = (int)request.getSession().getAttribute("userId");
		User user = UserDAO.getInstance().findUser(userId);
		
		User loggedUser = (User) request.getSession().getAttribute("user");

		
		DirectMessage message = new DirectMessage(messageDTO);
		message.setMessageDate(LocalDate.now());
		message.setSender(loggedUser);
		if(message.getSender().getRole() == Role.ADMINISTRATOR) {
			message.setMessageContext("|ADMIN " + message.getSender().getName() + "|" + message.getMessageContext());
		}
		message.setReceiver(user);
		
		
		return new DirectMessageDTO(dao.save(message));
	}
	
}
