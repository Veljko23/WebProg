package dto;


import beans.FriendshipRequest;
import beans.User;
import enums.FriendshipRequestStatus;
import utils.DateHelper;

public class FriendshipRequestDTO {
	
	private int id;
	private User sender;
	private User recepient;
	private FriendshipRequestStatus status;
	private String requestDate;
	
	public FriendshipRequestDTO() {}

	public FriendshipRequestDTO(int id, User sender, User recepient, FriendshipRequestStatus status,
			String requestDate) {
		super();
		this.id = id;
		this.sender = sender;
		this.recepient = recepient;
		this.status = status;
		this.requestDate = requestDate;
	}

	public FriendshipRequestDTO(FriendshipRequest request) {
		super();
		this.id = request.getId();
		this.sender = request.getSender();
		this.recepient = request.getRecepient();
		this.status = request.getStatus();
		this.requestDate = DateHelper.dateToString(request.getRequestDate());
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

	public User getRecepient() {
		return recepient;
	}

	public void setRecepient(User recepient) {
		this.recepient = recepient;
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
	
	
	
	

}
