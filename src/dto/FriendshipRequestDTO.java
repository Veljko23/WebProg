package dto;


import beans.FriendshipRequest;
import beans.User;
import dao.UserDAO;
import enums.FriendshipRequestStatus;
import utils.DateHelper;

public class FriendshipRequestDTO {
	
	private int id;
	private FriendshipRequestStatus status;
	private String requestDate;
	private int senderId;
	private int receiverId;
	private String senderName;
	
	public FriendshipRequestDTO() {}

	public FriendshipRequestDTO(int id, User sender, User recepient, FriendshipRequestStatus status,
			String requestDate) {
		super();
		this.id = id;
		this.status = status;
		this.requestDate = requestDate;
	}

	public FriendshipRequestDTO(FriendshipRequest request) {
		super();
		this.id = request.getId();
		this.status = request.getStatus();
		this.requestDate = DateHelper.dateToString(request.getRequestDate());
		this.senderId = request.getSender().getId();
		this.receiverId = request.getRecepient().getId();
		this.senderName = request.getSender().getName();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public FriendshipRequestStatus getStatus() {
		return status;
	}

	public void setStatus(FriendshipRequestStatus status) {
		this.status = status;
	}

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
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
