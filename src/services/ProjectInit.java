package services;

import dao.CommentDAO;
import dao.DirectMessageDAO;
import dao.FriendShipRequestDAO;
import dao.PostDAO;
import dao.UserDAO;

public class ProjectInit {
	
	private static ProjectInit instance = null;
	
	public static String contextPath;
	
	private ProjectInit(String contextPath) {
		
		this.contextPath = contextPath;
		
		CommentDAO.getInstance().loadComments(contextPath);
		DirectMessageDAO.getInstance().loadDirectMessages(contextPath);
		
		FriendShipRequestDAO.getInstance().loadFriendshipRequests(contextPath);
		
		PostDAO.getInstance().loadPosts(contextPath);;
		
		UserDAO.getInstance().loadUsers(contextPath);
		
		CommentDAO.getInstance().bindCommentPost();
		CommentDAO.getInstance().bindCommentUser();
		
		PostDAO.getInstance().bindPostUser();
		
		DirectMessageDAO.getInstance().bindMessageUser();
		FriendShipRequestDAO.getInstance().bindRequestUser();
		
		UserDAO.getInstance().bindUserPictures();
		UserDAO.getInstance().bindUserFriends();
		
	}
	
	public static ProjectInit getInstance(String contextPath) {
		if(instance == null)
			instance = new ProjectInit(contextPath);
		
		return instance;
	}

}
