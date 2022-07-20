package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

import beans.FriendshipRequest;
import beans.User;
import enums.FriendshipRequestStatus;
import utils.DateHelper;

public class FriendShipRequestDAO {
	private HashMap<Integer, FriendshipRequest> requests = new HashMap<Integer, FriendshipRequest>();

	private static FriendShipRequestDAO instance = null;
	private FriendShipRequestDAO() {}
	
	public static FriendShipRequestDAO getInstance() {
		if(instance == null)
			instance = new FriendShipRequestDAO();
		
		return instance;
	}
	
	public Collection<FriendshipRequest> findAll() {
		Collection<FriendshipRequest> allFriendshipRequest = requests.values();
		return allFriendshipRequest;
	}
	
	public FriendshipRequest findFriendshipRequest(int id) {
		return requests.containsKey(id) ? requests.get(id) : null;
	}

	
	public FriendshipRequest save(FriendshipRequest friendshipRequest) {
		int maxId = -1;
		for (int id : requests.keySet()) {
			if (id > maxId) {
				maxId = id;
			}
		}

		maxId++;
		friendshipRequest.setId(maxId);
		requests.put(maxId, friendshipRequest);
		return friendshipRequest;
	}

	public void loadFriendshipRequests(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/podaci/requests.txt");
			System.out.println(file.getCanonicalPath());
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			int id = 0;
			int senderId;
			User sender = null;
			int recepientId;
			User recepient = null;
			FriendshipRequestStatus status = null;
			LocalDate requestDate = null;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					id = Integer.parseInt(st.nextToken().trim());
					senderId = Integer.parseInt(st.nextToken().trim());
					sender = new User(senderId);
					recepientId = Integer.parseInt(st.nextToken().trim());
					recepient = new User(recepientId);
					FriendshipRequestStatus []statuses = FriendshipRequestStatus.values();
					status = statuses[Integer.parseInt(st.nextToken().trim())];
					requestDate = DateHelper.stringToDate(st.nextToken().trim());
				}
				requests.put(id, new FriendshipRequest(id, sender, recepient, status, requestDate));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}

	}
	
	public void bindRequestUser() {
		for(FriendshipRequest request: requests.values()) {
			int senderId = request.getSender().getId();
			int receiverId = request.getRecepient().getId();
			
			User sender = UserDAO.getInstance().findUser(senderId);
			User receiver = UserDAO.getInstance().findUser(receiverId);
			if(sender == null || receiver == null) {
				System.out.println("Nije pronadjen user. Greska!");
				continue;
			}
			
			request.setSender(sender);
			request.setRecepient(receiver);
			receiver.getRequests().add(request);
		}
	}

	public FriendshipRequest change(FriendshipRequest friendshipRequest) {
		requests.put(friendshipRequest.getId(), friendshipRequest);
		return friendshipRequest;
	}

	public FriendshipRequest delete(int id) {
		return requests.remove(id);
	}
}
