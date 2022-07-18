package beans;

import java.time.LocalDate;

public class DirectMessage {

	private int id;
	private User sender;
	private User receiver;
	private String messageContext;
	private LocalDate messageDate;
	
	public DirectMessage() {
		
	}
	

	public DirectMessage(int id, User sender, User receiver, String messageContext, LocalDate messageDate) {
		super();
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.messageContext = messageContext;
		this.messageDate = messageDate;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public String getMessageContext() {
		return messageContext;
	}

	public void setMessageContext(String messageContext) {
		this.messageContext = messageContext;
	}

	public LocalDate getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(LocalDate messageDate) {
		this.messageDate = messageDate;
	}
	
	
	
	
}
