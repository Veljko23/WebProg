package dto;


import beans.FriendshipRequest;
import beans.User;
import enums.FriendshipRequestStatus;
import utils.DateHelper;

public class FriendshipRequestDTO {
	
	private int id;
	private FriendshipRequestStatus status;
	private String requestDate;
	
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

}
