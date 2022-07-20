package beans;

import java.time.LocalDate;

import enums.FriendshipRequestStatus;

public class FriendshipRequest {
	private int id;
	private User sender;
	private User recepient;
	private FriendshipRequestStatus status;
	private LocalDate requestDate;
	
	public FriendshipRequest() {
		
	}
	

	public FriendshipRequest(int id, User sender, User recepient, FriendshipRequestStatus status,
			LocalDate requestDate) {
		super();
		this.id = id;
		this.sender = sender;
		this.recepient = recepient;
		this.status = status;
		this.requestDate = requestDate;
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

	public LocalDate getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
	}
	
	
}