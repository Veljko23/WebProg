package dto;


import beans.DirectMessage;
import beans.User;
import utils.DateHelper;

public class DirectMessageDTO {
	private int id;
	private String messageContext;
	private String messageDate;
	private int senderId;
	private int receiverId;
	private String senderName;
	
	public DirectMessageDTO() {}

	public DirectMessageDTO(int id, User sender, User receiver, String messageContext, String messageDate) {
		super();
		this.id = id;
		this.messageContext = messageContext;
		this.messageDate = messageDate;
	}

	public DirectMessageDTO(DirectMessage message) {
		super();
		this.id = message.getId();
		this.messageContext = message.getMessageContext();
		this.messageDate = DateHelper.dateToString(message.getMessageDate());
		this.senderId = message.getSender().getId();
		this.receiverId = message.getReceiver().getId();
		this.senderName = message.getSender().getName();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessageContext() {
		return messageContext;
	}

	public void setMessageContext(String messageContext) {
		this.messageContext = messageContext;
	}

	public String getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(String messageDate) {
		this.messageDate = messageDate;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public int getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	
}
